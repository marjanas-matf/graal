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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.graalvm.compiler.core.common.type.Stamp;
import org.graalvm.compiler.core.common.type.StampPair;
import org.graalvm.compiler.debug.DebugContext;
import org.graalvm.compiler.debug.DebugHandlersFactory;
import org.graalvm.compiler.graph.Graph.NodeEventListener;
import org.graalvm.compiler.graph.Graph.NodeEventScope;
import org.graalvm.compiler.graph.Node;
import org.graalvm.compiler.java.BytecodeParser;
import org.graalvm.compiler.java.BytecodeParserOptions;
import org.graalvm.compiler.java.GraphBuilderPhase;
import org.graalvm.compiler.nodes.CallTargetNode;
import org.graalvm.compiler.nodes.ConstantNode;
import org.graalvm.compiler.nodes.FrameState;
import org.graalvm.compiler.nodes.InvokeNode;
import org.graalvm.compiler.nodes.NodeView;
import org.graalvm.compiler.nodes.ParameterNode;
import org.graalvm.compiler.nodes.ReturnNode;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.calc.FloatingNode;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderConfiguration;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderContext;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderTool;
import org.graalvm.compiler.nodes.graphbuilderconf.InlineInvokePlugin;
import org.graalvm.compiler.nodes.graphbuilderconf.IntrinsicContext;
import org.graalvm.compiler.nodes.graphbuilderconf.ParameterPlugin;
import org.graalvm.compiler.nodes.java.LoadFieldNode;
import org.graalvm.compiler.nodes.java.NewArrayNode;
import org.graalvm.compiler.nodes.java.NewInstanceNode;
import org.graalvm.compiler.nodes.java.StoreFieldNode;
import org.graalvm.compiler.nodes.spi.UncheckedInterfaceProvider;
import org.graalvm.compiler.nodes.type.StampTool;
import org.graalvm.compiler.options.Option;
import org.graalvm.compiler.options.OptionValues;
import org.graalvm.compiler.phases.OptimisticOptimizations;
import org.graalvm.compiler.phases.util.Providers;
import org.graalvm.compiler.word.WordTypes;

import com.oracle.graal.pointsto.BigBang;
import com.oracle.graal.pointsto.infrastructure.GraphProvider.Purpose;
import com.oracle.graal.pointsto.meta.AnalysisMethod;
import com.oracle.graal.pointsto.meta.AnalysisType;
import com.oracle.graal.pointsto.meta.HostedProviders;
import com.oracle.graal.pointsto.util.CompletionExecutor.DebugContextRunnable;
import com.oracle.svm.core.annotate.NeverInline;
import com.oracle.svm.core.annotate.NeverInlineTrivial;
import com.oracle.svm.core.annotate.RestrictHeapAccess;
import com.oracle.svm.core.annotate.Uninterruptible;
import com.oracle.svm.core.option.HostedOptionKey;
import com.oracle.svm.core.util.VMError;
import com.oracle.svm.hosted.meta.HostedMethod;
import com.oracle.svm.hosted.phases.AnalysisGraphBuilderPhase.AnalysisBytecodeParser;
import com.oracle.svm.hosted.phases.NativeImageInlineDuringParsingPlugin.CallSite;
import com.oracle.svm.hosted.phases.NativeImageInlineDuringParsingPlugin.InvocationResult;
import com.oracle.svm.hosted.phases.NativeImageInlineDuringParsingPlugin.InvocationResultInline;
import com.oracle.svm.hosted.phases.SharedGraphBuilderPhase.SharedBytecodeParser;

import jdk.vm.ci.code.BailoutException;
import jdk.vm.ci.meta.ResolvedJavaMethod;

public class NativeImageInlineDuringParsingPlugin implements InlineInvokePlugin {

    public static class Options {
        @Option(help = "Inline methods during parsing before the static analysis.")//
        public static final HostedOptionKey<Boolean> InlineBeforeAnalysis = new HostedOptionKey<>(false);

        @Option(help = "Inlining is explored up to this number of nonparametric nodes in the graph.") public static final HostedOptionKey<Integer> InlineBeforeAnalysisMaxNumberOfNodes = new HostedOptionKey<>(
                        50);
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

        public void onCreateInvoke(GraphBuilderContext b, int invokeBci, boolean analysis) {
            if (b.getDepth() == 0) {
                ConcurrentMap<Integer, InvocationResult> map = bciMap(b.getMethod());
                if (analysis) {
                    map.putIfAbsent(invokeBci, InvocationResult.NO_ANALYSIS);
                }
            }
        }
    }

    private final boolean analysis;
    private final HostedProviders providers;
    private static int numberForInline = 0;

    public NativeImageInlineDuringParsingPlugin(boolean analysis, HostedProviders providers) {
        this.analysis = analysis;
        this.providers = providers;
    }

    public static int getNumberForInline() {
        return numberForInline;
    }

    @Override
    public InlineInfo shouldInlineInvoke(GraphBuilderContext b, ResolvedJavaMethod method, ValueNode[] args) {
        InvocationData data = ((SharedBytecodeParser) b).inlineInvocationData;
        if (data == null) {
            throw VMError.shouldNotReachHere("must not use NativeImageInlineDuringParsingPlugin when bytecode parser does not have InvocationData");
        }

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

        CallSite callSite = new CallSite(toAnalysisMethod(b.getMethod()), b.bci());

        InvocationResult inline;
        if (b.getDepth() > 0) {
            /*
             * We already decided to inline the first callee into the root method, so now
             * recursively inline everything.
             */
            inline = ((SharedBytecodeParser) b.getParent()).inlineDuringParsingState.children.get(callSite);

        } else {

            if (analysis) {
                BigBang bb = ((AnalysisBytecodeParser) b).bb;
                InvocationResult newResult;
                TrivialMethodDetector detector = new TrivialMethodDetector(bb, providers, ((SharedBytecodeParser) b).getGraphBuilderConfig(), b.getOptions(), b.getDebug());
                newResult = detector.analyzeMethod(callSite, (AnalysisMethod) method, args, null);
                InvocationResult existingResult = data.putIfAbsent(b.getMethod(), b.bci(), newResult);
                /*if (existingResult != null) {
                    throw VMError.shouldNotReachHere("Analysis result already present: " + b.getMethod().asStackTraceElement(b.bci()));
                }*/
                inline = newResult;
            } else {
                inline = data.get(b.getMethod(), b.bci());
            }
        }

        if (inline instanceof InvocationResultInline) {
            InvocationResultInline inlineData = (InvocationResultInline) inline;
            VMError.guarantee(inlineData.callee == toAnalysisMethod(method));

            VMError.guarantee(((SharedBytecodeParser) b).inlineDuringParsingState == null);
            ((SharedBytecodeParser) b).inlineDuringParsingState = inlineData;

            if (analysis) {
                AnalysisMethod aMethod = (AnalysisMethod) method;
                aMethod.registerAsImplementationInvoked(null);

                ensureParsed(((AnalysisBytecodeParser) b).bb, aMethod);

                if (!aMethod.isStatic() && args[0].isConstant()) {
                    AnalysisType receiverType = (AnalysisType) StampTool.typeOrNull(args[0]);
                    receiverType.registerAsInHeap();
                }
            }
            // System.out.println(method.format("INLINE: %n, %H"));
            numberForInline++;
            return InlineInfo.createStandardInlineInfo(method);
        } else {
            return null;
        }
    }

    private static void ensureParsed(BigBang bb, AnalysisMethod method) {
        bb.postTask(new DebugContextRunnable() {
            @Override
            public void run(DebugContext ignore) {
                method.getTypeFlow().ensureParsed(bb, null);
            }

            @Override
            public DebugContext getDebug(OptionValues options, List<DebugHandlersFactory> factories) {
                return DebugContext.disabled(options);
            }
        });
    }

    @Override
    public void notifyBeforeInline(GraphBuilderContext b, ResolvedJavaMethod methodToInline) {
        SharedBytecodeParser parser = (SharedBytecodeParser) b;
        InvocationResultInline inlineData = parser.inlineDuringParsingState;
        if (inlineData != null) {
            VMError.guarantee(inlineData.callee == toAnalysisMethod(methodToInline));
        }
    }

    @Override
    public void notifyAfterInline(GraphBuilderContext b, ResolvedJavaMethod methodToInline) {
        SharedBytecodeParser parser = (SharedBytecodeParser) b;
        InvocationResultInline inlineData = parser.inlineDuringParsingState;
        if (inlineData != null) {
            VMError.guarantee(inlineData.callee == toAnalysisMethod(methodToInline));
            parser.inlineDuringParsingState = null;
        }
    }

    static AnalysisMethod toAnalysisMethod(ResolvedJavaMethod method) {
        if (method instanceof AnalysisMethod) {
            return (AnalysisMethod) method;
        } else {
            return ((HostedMethod) method).getWrapped();
        }
    }
}

class TrivialMethodDetector {

    final BigBang bb;
    final HostedProviders providers;
    final GraphBuilderConfiguration prototypeGraphBuilderConfig;
    final OptionValues options;
    final DebugContext debug;

    TrivialMethodDetector(BigBang bb, HostedProviders providers, GraphBuilderConfiguration originalGraphBuilderConfig, OptionValues options, DebugContext debug) {
        this.bb = bb;
        this.debug = debug;
        this.prototypeGraphBuilderConfig = makePrototypeGraphBuilderConfig(originalGraphBuilderConfig);
        this.options = options;
        this.providers = providers;
    }

    private static GraphBuilderConfiguration makePrototypeGraphBuilderConfig(GraphBuilderConfiguration originalGraphBuilderConfig) {
        GraphBuilderConfiguration result = originalGraphBuilderConfig.copy();

        result.getPlugins().clearParameterPlugin();

        result.getPlugins().clearInlineInvokePlugins();
        for (InlineInvokePlugin inlineInvokePlugin : originalGraphBuilderConfig.getPlugins().getInlineInvokePlugins()) {
            if (!(inlineInvokePlugin instanceof NativeImageInlineDuringParsingPlugin)) {
                result.getPlugins().appendInlineInvokePlugin(inlineInvokePlugin);
            }
        }

        return result;
    }

    @SuppressWarnings("try")
    InvocationResult analyzeMethod(CallSite callSite, AnalysisMethod method, ValueNode[] args, Object existingSingleAllowedElement) {

        if (!method.hasBytecodes()) {
            /* Native method. */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;

        } else if (method.isSynchronized()) {
            /*
             * Synchronization operations will always bring us above the node limit, so no point in
             * starting an analysis.
             */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;

        } else if (method.buildGraph(debug, method, providers, Purpose.ANALYSIS) != null) {
            /* Method has a manually constructed graph via GraphProvider. */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;

        } else if (providers.getGraphBuilderPlugins().getInvocationPlugins().lookupInvocation(method) != null) {
            /* Method has an invocation plugin that we must not miss. */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;
        }

        MethodState methodState = new MethodState(new InvocationResultInline(callSite, method), existingSingleAllowedElement);

        GraphBuilderConfiguration graphBuilderConfig = prototypeGraphBuilderConfig.copy();
        graphBuilderConfig.getPlugins().appendInlineInvokePlugin(methodState);
        graphBuilderConfig.getPlugins().appendParameterPlugin(new TrivialMethodDetectorParameterPlugin(args));

        StructuredGraph graph = new StructuredGraph.Builder(options, debug).method(method).build();

        try (DebugContext.Scope ignored = debug.scope("InlineDuringParsingAnalysis", graph, method, this)) {

            TrivialMethodDetectorGraphBuilderPhase builderPhase = new TrivialMethodDetectorGraphBuilderPhase(bb, providers, graphBuilderConfig, OptimisticOptimizations.NONE, null,
                            providers.getWordTypes());

            try (NodeEventScope ignored1 = graph.trackNodeEvents(methodState)) {
                builderPhase.apply(graph);
            }

            debug.dump(1, graph, "InlineDuringParsingAnalysis successful");
            return methodState.result;

        } catch (Throwable ex) {
            debug.dump(1, graph, "InlineDuringParsingAnalysis failed with " + ex.toString());

            /*
             * Whatever happens during the analysis is non-fatal because we can just not inline that
             * invocation.
             */
            return InvocationResult.ANALYSIS_TOO_COMPLICATED;
        }
    }

    class MethodState extends NodeEventListener implements InlineInvokePlugin {

        final InvocationResultInline result;
        private int allNodeCount;
        private int parameterNodeCount;
        Object singleAllowedElement;

        MethodState(InvocationResultInline result, Object existingSingleAllowedElement) {
            this.result = result;
            this.singleAllowedElement = existingSingleAllowedElement;
            this.allNodeCount = 1; // root node
            this.parameterNodeCount = 0;
        }

        public int getAllNodeCount() {
            return allNodeCount;
        }

        public int getParameterNodeCount() {
            return parameterNodeCount;
        }

        public int getNonParameterNodeCount() {
            return getAllNodeCount() - getParameterNodeCount();
        }

        @Override
        public void nodeAdded(Node node) {

            allNodeCount += 1;

            if (node instanceof ConstantNode) {
                /* Nothing to do, an unlimited amount of constants is allowed. We like constants. */
            } else if (node instanceof FrameState) {
                /* Nothing to do. */
            } else if (node instanceof ParameterNode) {
                /* Nothing to do. */
                parameterNodeCount += 1;
            } else if (node instanceof ReturnNode) {
                /*
                 * Nothing to do, returning a value is fine. We don't allow control flow so there
                 * can never be more than one return.
                 */
            } else if (node instanceof InvokeNode || node instanceof LoadFieldNode || node instanceof StoreFieldNode || node instanceof NewInstanceNode || node instanceof NewArrayNode) {
                if (singleAllowedElement != null) {
                    throw new TrivialMethodDetectorBailoutException("Only a single element is allowed: new node " + node + ", existing element " + singleAllowedElement);
                }
                singleAllowedElement = node;

            } else if (node instanceof CallTargetNode) {
                /* Nothing to do. */

            } else {
                throw new TrivialMethodDetectorBailoutException("Node not allowed: " + node);
            }
        }

        @Override
        public InlineInfo shouldInlineInvoke(GraphBuilderContext b, ResolvedJavaMethod method, ValueNode[] args) {

            if (getNonParameterNodeCount() > NativeImageInlineDuringParsingPlugin.Options.InlineBeforeAnalysisMaxNumberOfNodes.getValue()) {
                /* Node limit is exceed */
                return null;
            }

            if (method.getAnnotation(NeverInline.class) != null || method.getAnnotation(NeverInlineTrivial.class) != null) {
                return null;
            }
            if (b.getDepth() > BytecodeParserOptions.InlineDuringParsingMaxDepth.getValue(b.getOptions())) {
                return null;
            }

            InvocationResult state = analyzeMethod(new CallSite((AnalysisMethod) b.getMethod(), b.bci()), (AnalysisMethod) method, args, singleAllowedElement);

            if (state instanceof InvocationResultInline) {

                if (b.getDepth() == 0) {
                    InvocationResultInline inlineState = (InvocationResultInline) state;
                    if (result.children.containsKey(inlineState.site)) {
                        throw new TrivialMethodDetectorBailoutException("Invoke already registered: " + inlineState.site);
                    }
                    result.children.put(inlineState.site, inlineState);
                }
                return InlineInfo.createStandardInlineInfo(method);
            } else {
                return null;
            }
        }
    }

}

class TrivialMethodDetectorBailoutException extends BailoutException {

    private static final long serialVersionUID = -1063600090362390263L;

    TrivialMethodDetectorBailoutException(String message) {
        super(message);
    }

    /**
     * For performance reasons, this exception does not record any stack trace information.
     */
    @SuppressWarnings("sync-override")
    @Override
    public final Throwable fillInStackTrace() {
        return this;
    }
}

class TrivialMethodDetectorGraphBuilderPhase extends AnalysisGraphBuilderPhase {

    TrivialMethodDetectorGraphBuilderPhase(BigBang bb, Providers providers,
                    GraphBuilderConfiguration graphBuilderConfig, OptimisticOptimizations optimisticOpts, IntrinsicContext initialIntrinsicContext, WordTypes wordTypes) {
        super(bb, providers, graphBuilderConfig, optimisticOpts, initialIntrinsicContext, wordTypes, null);
    }

    @Override
    protected BytecodeParser createBytecodeParser(StructuredGraph graph, BytecodeParser parent, ResolvedJavaMethod method, int entryBCI, IntrinsicContext intrinsicContext) {
        return new TrivialMethodDetectorBytecodeParser(bb, this, graph, parent, method, entryBCI, intrinsicContext);
    }

}

class TrivialMethodDetectorBytecodeParser extends AnalysisBytecodeParser {
    protected TrivialMethodDetectorBytecodeParser(BigBang bb, GraphBuilderPhase.Instance graphBuilderInstance, StructuredGraph graph, BytecodeParser parent, ResolvedJavaMethod method, int entryBCI,
                    IntrinsicContext intrinsicContext) {
        super(bb, graphBuilderInstance, graph, parent, method, entryBCI, intrinsicContext, null);
    }

    @Override
    protected boolean needsExplicitNullCheckException(ValueNode object) {
        if (currentBlock.exceptionDispatchBlock() != null) {
            throw new TrivialMethodDetectorBailoutException("Null check inside exception handler");
        }
        return false;
    }
}

class TrivialMethodDetectorParameterPlugin implements ParameterPlugin {

    private final ValueNode[] args;

    TrivialMethodDetectorParameterPlugin(ValueNode[] args) {
        this.args = args;
    }

    @Override
    public FloatingNode interceptParameter(GraphBuilderTool b, int index, StampPair stamp) {
        ValueNode arg = args[index];
        Stamp argStamp = arg.stamp(NodeView.DEFAULT);
        if (arg.isConstant()) {
            return new ConstantNode(arg.asConstant(), argStamp);
        } else {
            StampPair stampPair;
            if (arg instanceof UncheckedInterfaceProvider) {
                stampPair = StampPair.create(argStamp, ((UncheckedInterfaceProvider) arg).uncheckedStamp());
            } else {
                stampPair = StampPair.createSingle(argStamp);
            }
            return new ParameterNode(index, stampPair);
        }
    }
}
