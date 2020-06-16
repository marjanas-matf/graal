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
package com.oracle.svm.test;

import com.oracle.svm.hosted.phases.NativeImageInlineDuringParsingPlugin;
import jdk.vm.ci.meta.ResolvedJavaMethod;
import org.graalvm.compiler.core.test.GraalCompilerTest;
import org.graalvm.compiler.graph.Node;
import org.graalvm.compiler.java.BytecodeParserOptions;
import org.graalvm.compiler.nodes.Invoke;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.options.OptionValues;
import org.junit.Test;

/*

These tests will be run by the command:

mx --dy /substratevm unittest --suite=substratevm  NativeImageInlineDuringParsingPluginTest

........................................
MxJUnitCore
JUnit version 4.12
........................................
Time: 0.492

OK (40 tests)

 */

public class NativeImageInlineDuringParsingPluginTest extends GraalCompilerTest {

    @Test
    public void testEmpty() {
        assertInlined(getGraph(SourceCodeForTests.class, "empty"));
    }

    @Test
    public void testSumOfTwo() {
        assertInlined(getGraph(SourceCodeForTests.class, "sumOfTwo"));
    }

    @Test
    public void testSumOfTen() {
        assertInlined(getGraph(SourceCodeForTests.class, "sumOfTen"));
    }

    @Test
    public void testSunOfTenWithLocal() {
        assertInlined(getGraph(SourceCodeForTests.class, "sumOfTenWithLocal"));
    }

    @Test
    public void testTwo() {
        assertInlined(getGraph(SourceCodeForTests.class, "two"));
    }

    @Test
    public void testGetNumber() {
        assertInlined(getGraph(SourceCodeForTests.class, "getNumber"));
    }

    @Test
    public void testAllocation1() {
        assertInlined(getGraph(SourceCodeForTests.class, "allocation1"));
    }

    @Test
    public void testAllocation2() {
        assertInlined(getGraph(SourceCodeForTests.class, "allocation2"));
    }

    @Test
    public void testAllocation3() {
        assertInlined(getGraph(SourceCodeForTests.class, "allocation3"));
    }


    @Test
    public void testAllocation4() {
        assertInlined(getGraph(SourceCodeForTests.class, "allocation4"));
    }

    @Test
    public void testAllocation5() {
        assertInlined(getGraph(SourceCodeForTests.class, "allocation5"));
    }

    @Test
    public void testLoop1() {
        assertInlined(getGraph(SourceCodeForTests.class, "loop1"));
    }

    @Test
    public void testLoop2() {
        assertInlined(getGraph(SourceCodeForTests.class, "loop2"));
    }

    @Test
    public void testLoop3() {
        assertInlined(getGraph(SourceCodeForTests.class, "loop3"));
    }

    @Test
    public void testLoop4() {
        assertInlined(getGraph(SourceCodeForTests.class, "loop4"));
    }

    @Test
    public void testLoop5() {
        assertInlined(getGraph(SourceCodeForTests.class, "loop5"));
    }

    @Test
    public void testLoop6() {
        assertInlined(getGraph(SourceCodeForTests.class, "loop6"));
    }

    @Test
    public void testMin() {
        assertInlined(getGraph(SourceCodeForTests.class, "min"));
    }

    @Test
    public void testMax() {
        assertInlined(getGraph(SourceCodeForTests.class, "max"));
    }

    @Test
    public void testMax2() {
        assertInlined(getGraph(SourceCodeForTests.class, "max2"));
    }

    @Test
    public void testSwitch1() {
        assertInlined(getGraph(SourceCodeForTests.class, "switch1"));
    }

    @Test
    public void testSwitch2() {
        assertInlined(getGraph(SourceCodeForTests.class, "switch2"));
    }

    @Test
    public void testSwitch3() {
        assertInlined(getGraph(SourceCodeForTests.class, "switch3"));
    }

    @Test
    public void testArithmetic1() {
        assertInlined(getGraph(SourceCodeForTests.class, "arithmetic1"));
    }

    @Test
    public void testArithmetic2() {
        assertInlined(getGraph(SourceCodeForTests.class, "arithmetic2"));
    }

    @Test
    public void testArithmetic3() {
        assertInlined(getGraph(SourceCodeForTests.class, "arithmetic3"));
    }

    @Test
    public void testArithmetic4() {
        assertInlined(getGraph(SourceCodeForTests.class, "arithmetic4"));
    }

    @Test
    public void testArithmetic5() {
        assertInlined(getGraph(SourceCodeForTests.class, "arithmetic5"));
    }

    @Test
    public void testArithmetic6() {
        assertInlined(getGraph(SourceCodeForTests.class, "arithmetic6"));
    }

    @Test
    public void testBoolean1() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean1"));
    }

    @Test
    public void testBoolean2() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean2"));
    }

    @Test
    public void testBoolean3() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean3"));
    }

    @Test
    public void testBoolean4() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean4"));
    }

    @Test
    public void testBoolean5() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean5"));
    }

    @Test
    public void testBoolean6() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean6"));
    }

    @Test
    public void testBoolean7() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean7"));
    }

    @Test
    public void testBoolean8() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean8"));
    }

    @Test
    public void testBoolean9() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean9"));
    }

    @Test
    public void testBoolean10() {
        assertInlined(getGraph(SourceCodeForTests.class, "boolean10"));
    }

    @Test
    public void testGetNameOfClass() {
        assertInlined(getGraph(SourceCodeForTests.class, "getNameOfClass"));
    }

    @Test
    public void testGetPackageName() {
        assertInlined(getGraph(SourceCodeForTests.class, "getPackageName"));
    }

    @SuppressWarnings("try")
    private StructuredGraph getGraph(Class<?> clazz, String methodName) {
        ResolvedJavaMethod method = getResolvedJavaMethod(clazz, methodName);
        StructuredGraph graph = parseForCompile(method, enableInlineBeforeAnalysis());
        createInliningPhase().apply(graph, getDefaultHighTierContext());
        return graph;

    }

    private static StructuredGraph assertInlined(StructuredGraph graph) {
        // number of invoke nodes must be 0
        for (Node node : graph.getNodes()) {
            if (node instanceof Invoke) {
                fail(node.toString());
            }
        }
        return graph;
    }

    private static OptionValues enableInlineBeforeAnalysis() {
        return new OptionValues(getInitialOptions(), NativeImageInlineDuringParsingPlugin.Options.InlineBeforeAnalysis, true,
                BytecodeParserOptions.InlineDuringParsing, false, BytecodeParserOptions.InlineIntrinsicsDuringParsing, false);
    }

}

class SourceCodeForTests {
    public static void empty() {
    }

    public static double sumOfTwo(double var0, double var2) {
        return var0 + var2;
    }

    public static int sumOfTen(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        return var0 + var1 + var2 + var3 + var4 + var5 + var6 + var7 + var8 + var9;
    }

    public static int sumOfTenWithLocal(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        int var10 = var0 + var1 + var2 + var3 + var4 + var5 + var6 + var7 + var8 + var9;
        return var10;
    }

    public static int two() {
        return 2;
    }

    static int number = 10;

    public static int getNumber() {
        return number;
    }

    public static int allocation1() {
        SimpleClass obj1 = new SimpleClass(10, 10);
        return obj1.getAverage();
    }

    public static int allocation2(int a, int b) {
        SimpleClass obj1 = new SimpleClass(a, b);
        return obj1.getAverage();
    }

    public static int allocation3(int a, int b, int c, int d) {
        SimpleClass obj1 = new SimpleClass(a, b);
        SimpleClass obj2 = new SimpleClass(c, d);
        return obj1.getAverage() + obj2.getAverage();
    }

    public static int allocation4(int a, int b, int c) {
        SimpleClass obj1 = new SimpleClass(a, a);
        SimpleClass obj2 = new SimpleClass(b, b);
        SimpleClass obj3 = new SimpleClass(c, c);
        return obj1.getAverage() + obj2.getAverage() + obj3.getAverage();
    }

    public static int allocation5() {
        SimpleClass obj1 = new SimpleClass(1, 100);
        obj1.setX(obj1.getX() * 2);
        obj1.setY(obj1.getY() * 2);
        return obj1.callAverage();
    }

    public static double arithmetic1() {
        return 1 + 2 + 3;
    }

    public static double arithmetic2(double a, double b, double c) {
        return a + b + c;
    }

    public static double arithmetic3(double a, double b, double c) {
        return (a + b + c) / 3;
    }

    public static double arithmetic4(double a, double b, double c) {
        double sum = a + b + c;
        double doubleSum = sum * 2;
        return doubleSum;
    }

    public static double arithmetic5(double a, double b, double c) {
        double result = 0;
        for (int i = 0; i < 5; i++) {
            result += a;
            result /= b;
            result *= c;
        }
        return result;
    }

    public static double arithmetic6(double a, double b, double c) {
        if (b != 0 && c != 0) {
            return a / b / c;
        } else {
            return a;
        }
    }

    public static void loop1(int a) {
        int i = 0;
        while (i < a) {
            i += 1;
        }
    }

    public static void loop2(int a) {
        for (int i = 0; i < a; i++) {
        }
    }

    public static void loop3(int a) {
        SimpleClass obj = new SimpleClass(a, a);
        for (int i = 0; i < a; i++) {
            obj.getX();
            obj.getY();
        }
    }

    public static void loop4(int a) {
        SimpleClass obj = new SimpleClass(a, a);
        for (int i = 0; i < a; i++) {
            obj.setX(i);
        }
    }

    public static void loop5(int a, int b) {
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {

            }
        }
    }

    public static void loop6(int a, int b) {
        int i = 0, j = 0;
        while (i < a) {
            while (j < b) {
                i++;
                j++;
            }
        }

    }

    public static int switch1(int a) {
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

    public static int switch2(int a, int b) {
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

    public static double switch3(int a, int b, int c) {
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


    public static int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    public static int max(int a, int b) {
        return a <= b ? b : a;
    }

    public static int max2(int a, int b) {
        return a >= b ? a : b;
    }

    public static boolean boolean1() {
        return true;
    }

    public static boolean boolean2() {
        return false;
    }

    public static boolean boolean3() {
        return 2 > 10;
    }

    public static boolean boolean4(int a, int b, int c) {
        return a > b ? true : (b > c ? true : false);
    }

    public static boolean boolean5(boolean a, boolean b, boolean c) {
        boolean and = a && b && c;
        boolean or = a || b || c;
        return and != or;
    }

    public static int boolean6(int a) {
        int mask = 1;
        return a ^ 1;
    }

    public static int boolean7(int a, int b) {
        return a ^ b;
    }

    public static boolean boolean8(int a, int b, int c) {
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

    public static int boolean9(int a) {
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

    public static int boolean10(int a) {
        int numberOf1 = 0;
        int mask = 1 << 31;
        for (; mask != 0; mask >>= 1) {
            if ((a & mask) == 1) {
                numberOf1++;
            }
        }
        return numberOf1;
    }

    public static String getNameOfClass() {
        return "SimpleClass";
    }

    public static String getPackageName() {
        return "org.graalvm.compiler.core.test.inlining;";
    }

}

class SimpleClass {

    public int x;
    public int y;

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
