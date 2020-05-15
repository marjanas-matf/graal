
/*
 * Copyright (c) 2013, 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.svm.hosted.phases;

import com.oracle.graal.pointsto.meta.AnalysisMethod;
import com.oracle.graal.pointsto.meta.AnalysisType;
import com.oracle.graal.pointsto.meta.HostedProviders;
import com.oracle.graal.pointsto.util.CompletionExecutor.DebugContextRunnable;
import com.oracle.svm.core.annotate.NeverInline;
import com.oracle.svm.core.annotate.NeverInlineTrivial;
import com.oracle.svm.core.annotate.RestrictHeapAccess;
import com.oracle.svm.core.annotate.Uninterruptible;
import com.oracle.svm.core.jdk.InternalVMMethod;
import com.oracle.svm.core.option.HostedOptionKey;
import com.oracle.svm.core.snippets.SubstrateForeignCallTarget;
import com.oracle.svm.core.util.VMError;
import com.oracle.svm.hosted.meta.HostedMethod;
import com.oracle.svm.hosted.phases.AnalysisGraphBuilderPhase.AnalysisBytecodeParser;
import jdk.vm.ci.meta.ResolvedJavaMethod;
import org.graalvm.compiler.api.replacements.Snippet;
import org.graalvm.compiler.debug.DebugContext;
import org.graalvm.compiler.graph.Node;
import org.graalvm.compiler.nodes.FrameState;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.extended.ForeignCallNode;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderConfiguration;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderContext;
import org.graalvm.compiler.nodes.graphbuilderconf.InlineInvokePlugin;
import org.graalvm.compiler.nodes.java.LoadFieldNode;
import org.graalvm.compiler.nodes.java.StoreFieldNode;
import org.graalvm.compiler.nodes.type.StampTool;
import org.graalvm.compiler.options.Option;
import org.graalvm.compiler.phases.OptimisticOptimizations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.oracle.graal.pointsto.infrastructure.GraphProvider.Purpose;
import static com.oracle.svm.hosted.phases.SharedGraphBuilderPhase.SharedBytecodeParser;

public class NativeImageInlineDuringParsingPlugin implements InlineInvokePlugin {

    // to use this plugin: native-image Example -H:+InlineBeforeAnalysis
    public static class Options {
        @Option(help = "Inline methods during parsing before the static analysis.")//
        public static final HostedOptionKey<Boolean> InlineBeforeAnalysis = new HostedOptionKey<>(false);

    }

    static final class CallSite {
        final AnalysisMethod caller;
        final int bci;

        CallSite(AnalysisMethod caller, int bci) {
            this.caller = caller;
            this.bci = bci;
        }

        @Override
        public int hashCode() {
            return caller.hashCode() * 31 + bci;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CallSite other = (CallSite) obj;
            return bci == other.bci && caller.equals(other.caller);
        }

        @Override
        public String toString() {
            return caller.format("%h.%n(%p)") + "@" + bci;
        }
    }

    static class InvocationResult {
        static final InvocationResult ANALYSIS_TOO_COMPLICATED = new InvocationResult();
        static final InvocationResult NO_ANALYSIS = new InvocationResult();
    }

    public static class InvocationResultInline extends InvocationResult {
        final CallSite site;
        final AnalysisMethod callee;
        final Map<CallSite, InvocationResultInline> children;

        public InvocationResultInline(CallSite site, AnalysisMethod callee) {
            this.site = site;
            this.callee = callee;
            this.children = new HashMap<>();
        }

        @Override
        public String toString() {
            return append(new StringBuilder(), "").toString();
        }

        private StringBuilder append(StringBuilder sb, String indentation) {
            sb.append(site).append(" -> ").append(callee.format("%h.%n(%p)"));
            String newIndentation = indentation + "  ";
            for (InvocationResultInline child : children.values()) {
                sb.append(System.lineSeparator()).append(newIndentation);
                child.append(sb, newIndentation);
            }
            return sb;
        }
    }

    public static class InvocationData {
        private final ConcurrentMap<AnalysisMethod, ConcurrentMap<Integer, InvocationResult>> data = new ConcurrentHashMap<>();

        private ConcurrentMap<Integer, InvocationResult> bciMap(ResolvedJavaMethod method) {
            AnalysisMethod key;
            if (method instanceof AnalysisMethod) {
                key = (AnalysisMethod) method;
            } else {
                key = ((HostedMethod) method).getWrapped();
            }

            return data.computeIfAbsent(key, unused -> new ConcurrentHashMap<>());
        }

        InvocationResult get(ResolvedJavaMethod method, int bci) {
            return bciMap(method).get(bci);
        }

        InvocationResult putIfAbsent(ResolvedJavaMethod method, int bci, InvocationResult value) {
            return bciMap(method).putIfAbsent(bci, value);
        }

        public void onCreateInvoke(GraphBuilderContext b, int invokeBci, boolean analysis, ResolvedJavaMethod callee) {
            if (b.getDepth() == 0) {

                ConcurrentMap<Integer, InvocationResult> map = bciMap(b.getMethod());
                if (analysis) {
                    map.putIfAbsent(invokeBci, InvocationResult.NO_ANALYSIS);
                } else {
                    InvocationResult state = map.get(invokeBci);
                    if (state != InvocationResult.ANALYSIS_TOO_COMPLICATED && state != InvocationResult.NO_ANALYSIS) {
                        throw VMError.shouldNotReachHere("Missing information for call site: " + b.getMethod().asStackTraceElement(invokeBci));
                    }
                }
            }
        }
    }


    private final boolean analysis;
    private final HostedProviders providers;
    private static final ConcurrentHashMap<AnalysisMethod, InvocationResult> dataInline = new ConcurrentHashMap<>();
    private static final Object NULL_MARKER = new Object();

    public NativeImageInlineDuringParsingPlugin(boolean analysis, HostedProviders providers) {
        this.analysis = analysis;
        this.providers = providers;
    }

    public static void printDataInline() {
        if (dataInline.isEmpty()) {
            System.out.println("No inline info");
        } else {
            dataInline.forEach((m, r) -> System.out.println("method: " + m.format("%n, %H") + ", result: " + r.toString()));
        }
    }

    private static InvocationResult getResult(ResolvedJavaMethod method) {
        AnalysisMethod key;
        if (method instanceof AnalysisMethod) {
            key = (AnalysisMethod) method;
        } else {
            key = ((HostedMethod) method).getWrapped();
        }
        return dataInline.get(key);
    }

    @Override
    public InlineInfo shouldInlineInvoke(GraphBuilderContext b, ResolvedJavaMethod method, ValueNode[] args) {

        if (b.parsingIntrinsic()) {
            /* We are not interfering with any intrinsic method handling. */
            return null;
        }

        if (method.getAnnotation(NeverInline.class) != null || method.getAnnotation(NeverInlineTrivial.class) != null) {
            return null;
        }

        if (method.getAnnotation(RestrictHeapAccess.class) != null || method.getAnnotation(Uninterruptible.class) != null ||
                b.getMethod().getAnnotation(RestrictHeapAccess.class) != null || b.getMethod().getAnnotation(Uninterruptible.class) != null) {
            /*
             * Caller or callee have an annotation that might prevent inlining. We don't check the
             * exact condition but instead always bail out for simplicity.
             */
            return null;
        }

        if (b.getMethod().getAnnotation(Snippet.class) != null) {
            return null;
        }

        if (b.getMethod().getAnnotation(SubstrateForeignCallTarget.class) != null ||
                method.getAnnotation(SubstrateForeignCallTarget.class) != null) {
            return null;
        }

        if (b.getMethod().getDeclaringClass().isAnnotationPresent(InternalVMMethod.class)) {
            /* We are not interfering with any internal vmmethod */
            return null;
        }

        if (method.getDeclaringClass().isAnnotationPresent(InternalVMMethod.class)) {
            /* We are not interfering with any internal vmmethod */
            return null;
        }

        CallSite callSite = new CallSite(toAnalysisMethod(b.getMethod()), b.bci());

        InvocationResult inline;
        if (b.getDepth() > 0) {
            /*
             * We already decided to inline the first callee into the root method, so now
             * recursively inline everything.
             */
            // inline = getResult(method);
            inline = ((SharedBytecodeParser) b.getParent()).inlineDuringParsingState != null ? ((SharedBytecodeParser) b.getParent()).inlineDuringParsingState.children.get(callSite) : null;
        } else {
            if (analysis) {
                InvocationResult newResult;
                newResult = analyzeMethod(b, (AnalysisMethod) method, callSite);
                if (newResult instanceof InvocationResultInline) {
                    InvocationResultInline inlineData = (InvocationResultInline) newResult;
                    if (((SharedBytecodeParser) b).inlineDuringParsingState == null) {
                        ((SharedBytecodeParser) b).inlineDuringParsingState = inlineData;
                    } else {
                       /* Object nonNullElement = inlineData != null ? inlineData : NULL_MARKER;
                        Object previous = ((SharedBytecodeParser) b).inlineDuringParsingState.children.putIfAbsent(inlineData.site, inlineData);
                        VMError.guarantee(previous == null || previous.equals(nonNullElement), "Newly inlined element (" + nonNullElement + ") different than the previous (" + previous + ")"); */
                    }
                }
                dataInline.putIfAbsent((AnalysisMethod) method, newResult);
                inline = newResult;
            } else {
                InvocationResult existingResult = getResult(method);
                /*if (existingResult == null) {
                    throw VMError.shouldNotReachHere("No analysis result present: " + method.format("%n, %H, caller") + b.getMethod().format("%n, %H"));
                }*/
                inline = existingResult;
            }
        }
        if (inline instanceof InvocationResultInline) {
            if (analysis) {
                AnalysisMethod aMethod = (AnalysisMethod) method;
                aMethod.registerAsImplementationInvoked(null);
                ((AnalysisBytecodeParser) b).bb.postTask(new DebugContextRunnable() {
                    @Override
                    public void run(DebugContext ignore) {
                        aMethod.getTypeFlow().ensureParsed(((AnalysisBytecodeParser) b).bb, null);
                    }
                });
                if (!aMethod.isStatic() && args[0].isConstant()) {
                    AnalysisType receiverType = (AnalysisType) StampTool.typeOrNull(args[0]);
                    receiverType.registerAsInHeap();
                }
            }
            System.out.println("Method to inline: " + method.format("%n, %H") + b.getMethod().format(", caller: %n, %H"));
            return InlineInfo.createStandardInlineInfo(method);
        } else {
            return null;
        }
    }

    InvocationResult analyzeMethod(GraphBuilderContext b, AnalysisMethod method, CallSite callSite) {
        if (!method.hasBytecodes()) {
            /* Native method. */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;
        } else if (method.isSynchronized()) {
            /*
             * Synchronization operations will always bring us above the node limit, so no point in
             * starting an analysis.
             */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;

        } else if (method.buildGraph(b.getDebug(), method, providers, Purpose.ANALYSIS) != null) {
            /* Method has a manually constructed graph via GraphProvider. */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;

        } else if (providers.getGraphBuilderPlugins().getInvocationPlugins().lookupInvocation(method) != null) {
            /* Method has an invocation plugin that we must not miss. */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;
        }
        // get graph for callee
        StructuredGraph graph = new StructuredGraph.Builder(b.getOptions(), b.getDebug()).method(method).build();
        GraphBuilderConfiguration graphConfig = ((SharedBytecodeParser) b).getGraphBuilderConfig().copy();

        graphConfig.getPlugins().clearParameterPlugin();

        graphConfig.getPlugins().clearInlineInvokePlugins();
        for (InlineInvokePlugin inlineInvokePlugin : ((SharedBytecodeParser) b).getGraphBuilderConfig().getPlugins().getInlineInvokePlugins()) {
            if (!(inlineInvokePlugin instanceof NativeImageInlineDuringParsingPlugin)) {
                graphConfig.getPlugins().appendInlineInvokePlugin(inlineInvokePlugin);
            }
        }

        AnalysisGraphBuilderPhase graphbuilder = new AnalysisGraphBuilderPhase(((AnalysisBytecodeParser) b).bb, providers, graphConfig, OptimisticOptimizations.NONE, null, providers.getWordTypes());
        graphbuilder.apply(graph);

        int countFrameStates = 0;
        FrameState frameState = null;
        boolean hasLoadField = false;
        boolean hasStoreField = false;

        for (Node node : graph.getNodes()) {
            if (node instanceof LoadFieldNode) {
                hasLoadField = true;
            }
            if (node instanceof StoreFieldNode) {
                hasStoreField = true;
            }
            if (node instanceof FrameState) {
                countFrameStates++;
                frameState = (FrameState) node;
            }
            if (node instanceof ForeignCallNode) {
                return InvocationResult.ANALYSIS_TOO_COMPLICATED;
            }
        }

        if (countFrameStates == 0) {
            return new InvocationResultInline(callSite, method);
        }
        if (countFrameStates == 1 && frameState.stackSize() == 0) {
            return new InvocationResultInline(callSite, method);
        }
        /* last frame state with stack size zero */
        if (frameState.stackSize() == 0) {
            if (hasStoreField || hasLoadField) {
                return new InvocationResultInline(callSite, method);
            }
        }
        return InvocationResult.ANALYSIS_TOO_COMPLICATED;
    }

    static AnalysisMethod toAnalysisMethod(ResolvedJavaMethod method) {
        if (method instanceof AnalysisMethod) {
            return (AnalysisMethod) method;
        } else {
            return ((HostedMethod) method).getWrapped();
        }
    }
}
