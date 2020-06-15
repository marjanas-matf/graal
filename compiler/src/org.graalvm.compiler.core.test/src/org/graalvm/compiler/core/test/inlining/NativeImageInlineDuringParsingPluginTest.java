/*
 * Copyright (c) 2012, 2020, Oracle and/or its affiliates. All rights reserved.
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
package org.graalvm.compiler.core.test.inlining;

import jdk.vm.ci.meta.ResolvedJavaMethod;
import org.graalvm.compiler.core.test.GraalCompilerTest;
import org.graalvm.compiler.graph.Node;
import org.graalvm.compiler.nodes.Invoke;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.phases.OptimisticOptimizations;
import org.graalvm.compiler.phases.PhaseSuite;
import org.graalvm.compiler.phases.common.DeadCodeEliminationPhase;
import org.graalvm.compiler.phases.tiers.HighTierContext;
import org.junit.Test;

/*

These tests will be run by the command:

mx --dy /compiler unittest --suite=compiler -Dgraal.Dump= -Dgraal.MethodFilter=NativeImageInlineDuringParsingPluginTest NativeImageInlineDuringParsingPluginTest

........................................
MxJUnitCore
JUnit version 4.12
........................................
Time: 0.492

OK (40 tests)

 */

public class NativeImageInlineDuringParsingPluginTest extends GraalCompilerTest {

    /* TESTS */
    @Test
    public void testEmpty() {
        assertInlined(getGraph("empty"));
    }

    @Test
    public void testSumOfTwo() {
        assertInlined(getGraph("sumOfTwo"));
    }

    @Test
    public void testSumOfTen() {
        assertInlined(getGraph("sumOfTen"));
    }

    @Test
    public void testSunOfTenWithLocal() {
        assertInlined(getGraph("sumOfTenWithLocal"));
    }

    @Test
    public void testTwo() {
        assertInlined(getGraph("two"));
    }

    @Test
    public void testGetNumber() {
        assertInlined(getGraph("getNumber"));
    }

    @Test
    public void testAllocation1() {
        assertInlined(getGraph("allocation1"));
    }

    @Test
    public void testAllocation2() {
        assertInlined(getGraph("allocation2"));
    }

    @Test
    public void testAllocation3() {
        assertInlined(getGraph("allocation3"));
    }


    @Test
    public void testAllocation4() {
        assertInlined(getGraph("allocation4"));
    }

    @Test
    public void testAllocation5() {
        assertInlined(getGraph("allocation5"));
    }

    @Test
    public void testLoop1() {
        assertInlined(getGraph("loop1"));
    }

    @Test
    public void testLoop2() {
        assertInlined(getGraph("loop2"));
    }

    @Test
    public void testLoop3() {
        assertInlined(getGraph("loop3"));
    }

    @Test
    public void testLoop4() {
        assertInlined(getGraph("loop4"));
    }

    @Test
    public void testLoop5() {
        assertInlined(getGraph("loop5"));
    }

    @Test
    public void testLoop6() {
        assertInlined(getGraph("loop6"));
    }

    @Test
    public void testMin() {
        assertInlined(getGraph("min"));
    }

    @Test
    public void testMax() {
        assertInlined(getGraph("max"));
    }

    @Test
    public void testSwitch1() {
        assertInlined(getGraph("switch1"));
    }

    @Test
    public void testSwitch2() {
        assertInlined(getGraph("switch2"));
    }

    @Test
    public void testSwitch3() {
        assertInlined(getGraph("switch3"));
    }

    @Test
    public void testArithmetic1() {
        assertInlined(getGraph("arithmetic1"));
    }

    @Test
    public void testArithmetic2() {
        assertInlined(getGraph("arithmetic2"));
    }

    @Test
    public void testArithmetic3() {
        assertInlined(getGraph("arithmetic3"));
    }

    @Test
    public void testArithmetic4() {
        assertInlined(getGraph("arithmetic4"));
    }

    @Test
    public void testArithmetic5() {
        assertInlined(getGraph("arithmetic5"));
    }

    @Test
    public void testArithmetic6() {
        assertInlined(getGraph("arithmetic6"));
    }

    @Test
    public void testBoolean1() {
        assertInlined(getGraph("boolean1"));
    }

    @Test
    public void testBoolean2() {
        assertInlined(getGraph("boolean2"));
    }

    @Test
    public void testBoolean3() {
        assertInlined(getGraph("boolean3"));
    }

    @Test
    public void testBoolean4() {
        assertInlined(getGraph("boolean4"));
    }

    @Test
    public void testBoolean5() {
        assertInlined(getGraph("boolean5"));
    }

    @Test
    public void testBoolean6() {
        assertInlined(getGraph("boolean6"));
    }

    @Test
    public void testBoolean7() {
        assertInlined(getGraph("boolean7"));
    }

    @Test
    public void testBoolean8() {
        assertInlined(getGraph("boolean8"));
    }

    @Test
    public void testBoolean9() {
        assertInlined(getGraph("boolean9"));
    }

    @Test
    public void testBoolean10() {
        assertInlined(getGraph("boolean10"));
    }

    @Test
    public void testGetNameOfClass() {
        assertInlined(getGraph("getNameOfClass"));
    }

    @Test
    public void testGetPackageName() {
        assertInlined(getGraph("getPackageName"));
    }

    /* SOURCE CODE */

    private static void empty() {
    }

    private static double sumOfTwo(double var0, double var2) {
        return var0 + var2;
    }

    private static int sumOfTen(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        return var0 + var1 + var2 + var3 + var4 + var5 + var6 + var7 + var8 + var9;
    }

    private static int sumOfTenWithLocal(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        int var10 = var0 + var1 + var2 + var3 + var4 + var5 + var6 + var7 + var8 + var9;
        return var10;
    }

    private static int two() {
        return 2;
    }

    static int number = 10;

    private static int getNumber() {
        return number;
    }

    private static int allocation1() {
        SimpleClass obj1 = new SimpleClass(10, 10);
        return obj1.getAverage();
    }

    private static int allocation2(int a, int b) {
        SimpleClass obj1 = new SimpleClass(a, b);
        return obj1.getAverage();
    }

    private static int allocation3(int a, int b, int c, int d) {
        SimpleClass obj1 = new SimpleClass(a, b);
        SimpleClass obj2 = new SimpleClass(c, d);
        return obj1.getAverage() + obj2.getAverage();
    }

    private static int allocation4(int a, int b, int c) {
        SimpleClass obj1 = new SimpleClass(a, a);
        SimpleClass obj2 = new SimpleClass(b, b);
        SimpleClass obj3 = new SimpleClass(c, c);
        return obj1.getAverage() + obj2.getAverage() + obj3.getAverage();
    }

    private static int allocation5() {
        SimpleClass obj1 = new SimpleClass(1, 100);
        obj1.setX(obj1.getX() * 2);
        obj1.setY(obj1.getY() * 2);
        return obj1.callAverage();
    }

    private static double arithmetic1() {
        return 1 + 2 + 3;
    }

    private static double arithmetic2(double a, double b, double c) {
        return a + b + c;
    }

    private static double arithmetic3(double a, double b, double c) {
        return (a + b + c) / 3;
    }

    private static double arithmetic4(double a, double b, double c) {
        double sum = a + b + c;
        double doubleSum = sum * 2;
        return doubleSum;
    }

    private static double arithmetic5(double a, double b, double c) {
        double result = 0;
        for (int i = 0; i < 5; i++) {
            result += a;
            result /= b;
            result *= c;
        }
        return result;
    }

    private static double arithmetic6(double a, double b, double c) {
        if (b != 0 && c != 0) {
            return a / b / c;
        } else {
            return a;
        }
    }

    private static void loop1(int a) {
        int i = 0;
        while (i < a) {
            i += 1;
        }
    }

    private static void loop2(int a) {
        for (int i = 0; i < a; i++) {
        }
    }

    private static void loop3(int a) {
        SimpleClass obj = new SimpleClass(a, a);
        for (int i = 0; i < a; i++) {
            obj.getX();
            obj.getY();
        }
    }

    private static void loop4(int a) {
        SimpleClass obj = new SimpleClass(a, a);
        for (int i = 0; i < a; i++) {
            obj.setX(i);
        }
    }

    private static void loop5(int a, int b) {
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {

            }
        }
    }

    private static void loop6(int a, int b) {
        int i = 0, j = 0;
        while (i < a) {
            while (j < b) {
                i++;
                j++;
            }
        }

    }

    private static int switch1(int a) {
        int result = 0;
        switch (a) {
            case 1:
                result += 1;
                break;
            case 2:
                result += 2;
                break;
            case 3:
                result += 3;
                break;
        }
        return result;
    }

    private static int switch2(int a, int b) {
        int result = 0;
        if (a < b) {
            switch (a) {
                case 1:
                    result += 1;
                    break;
                case 2:
                    result += 2;
                    break;
                case 3:
                    result += 3;
                    break;
            }
        } else {
            switch (b) {
                case 1:
                    result *= 1;
                    break;
                case 2:
                    result *= 2;
                    break;
                case 3:
                    result *= 3;
                    break;
            }
        }
        return result;
    }

    private static double switch3(int a, int b, int c) {
        double result = 100;
        if (a < b) {
            switch (a) {
                case 1:
                    result += 1;
                    break;
                case 2:
                    result += 2;
                    break;
                case 3:
                    result += 3;
                    break;
            }
        } else if (b < c) {
            switch (b) {
                case 1:
                    result *= 1;
                    break;
                case 2:
                    result *= 2;
                    break;
                case 3:
                    result *= 3;
                    break;
            }
        } else {
            switch (c) {
                case 1:
                    result /= 1;
                    break;
                case 2:
                    result /= 2;
                    break;
                case 3:
                    result /= 3;
                    break;
            }
        }
        return result;
    }


    private static int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    private static int max(int a, int b) {
        return a <= b ? b : a;
    }

    private static boolean boolean1() {
        return true;
    }

    private static boolean boolean2() {
        return false;
    }

    private static boolean boolean3() {
        return 2 > 10;
    }

    private static boolean boolean4(int a, int b, int c) {
        return a > b ? true : (b > c ? true : false);
    }

    private static boolean boolean5(boolean a, boolean b, boolean c) {
        boolean and = a && b && c;
        boolean or = a || b || c;
        return and != or;
    }

    private static int boolean6(int a) {
        int mask = 1;
        return a ^ 1;
    }

    private static int boolean7(int a, int b) {
        return a ^ b;
    }

    private static boolean boolean8(int a, int b, int c) {
        if (a > b) {
            if (b > c) {
                return true;
            } else {
                return false;
            }
        } else {
            if (b < c) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static int boolean9(int a) {
        int result = 0;
        int last;
        for (int i = 0; i < 32; i++) {
            last = a & 1;
            a >>= 1;
            result <<= 1;
            result |= last;
        }
        return result;
    }

    private static int boolean10(int a) {
        int numberOf1 = 0;
        int mask = 1 << 31;
        for (; mask != 0; mask >>= 1) {
            if ((a & mask) == 1) {
                numberOf1++;
            }
        }
        return numberOf1;
    }

    private static String getNameOfClass() {
        return "SimpleClass";
    }

    private static String getPackageName() {
        return "org.graalvm.compiler.core.test.inlining;";
    }

    static StructuredGraph assertInlined(StructuredGraph graph) {
        // invoke is not in the graph
        for (Node node : graph.getNodes()) {
            if (node instanceof Invoke) {
                fail(node.toString());
            }
        }
        return graph;
    }

    @SuppressWarnings("try")
    private StructuredGraph getGraph(final String methodName) {
        ResolvedJavaMethod method = getResolvedJavaMethod(methodName);
        StructuredGraph.Builder builder = builder(method, StructuredGraph.AllowAssumptions.YES);
        StructuredGraph graph = parse(builder, getEagerGraphBuilderSuite());
        PhaseSuite<HighTierContext> graphBuilderSuite = getDefaultGraphBuilderSuite();
        HighTierContext context = new HighTierContext(getProviders(), graphBuilderSuite, OptimisticOptimizations.ALL);
        createCanonicalizerPhase().apply(graph, context);
        createInliningPhase().apply(graph, context);
        new DeadCodeEliminationPhase().apply(graph);
        return graph;

    }

}

class SimpleClass {

    private int x;
    private int y;

    SimpleClass(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAverage() {
        int sum = getX() + getY();
        sum /= 2;
        return sum;
    }

    public int callAverage() {
        return this.getAverage();
    }

}
