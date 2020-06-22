public class TestClass {
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

    public static void callSwitch3(int a, int b, int c){
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
        switch3(a,b,c);
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

    public static double switch4(int a, int b, int c, int d) {
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
        } else if (c<d){
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
        } else {
            switch (d) {
                case 1:
                    result -= 1;
                    break;
                case 2:
                    result -= 2;
                    break;
                case 3:
                    result -= 3;
                    break;
            }
        }
        return result;
    }

    public static void callSwitch(int a, int b, int c) {
        switch1(a);
        switch2(a,b);
        switch3(a,b,c);
        switch1(b);
        switch2(b,c);
        switch3(c,b,a);
        switch1(c);
        switch2(a,c);
        switch3(b,c,a);
        switch4(a,a,b,b);
        switch4(b,b,c,c);
        switch4(c,c,a,a);

    }

    public static String getNameOfClass() {
        return "SimpleClass";
    }

    public static String getPackageName() {
        return "org.graalvm.compiler.core.test.inlining;";
    }

    public static void printLine(int a) {
        System.out.println("This is one number, " + a);
    }

    public static void main(String[] args){
        SimpleClass obj = new SimpleClass(10,20);
        obj.setX(obj.getX()+5);
        obj.setY(obj.getY()+10);
        obj.getX();
        two();
        getNumber();
        switch3(2,3,4);
        callSwitch3(5,6,7);
        switch4(1,2,3,4);
        callSwitch(5,6,7);
        printLine(10);
        System.out.println(obj.getAverage());
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
