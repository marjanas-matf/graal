public class SimpleMethods {

    private int x;
    private int y;
    static int number;

    SimpleMethods(int x, int y){this.x = x; this.y = y;};

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getAverage(){
        return getX();
    }

    public int callAverage(){
        return getAverage();
    }

    public static int two(){
        return 2;
    }

    public static int getNumber(){
        return number;
    }

    public static void empty(){ }

    public static void callEmpty(){ empty(); }

    public static double sumOfTwo(double x, double y){
        return x+y;
    }

    public static int sumOfTen(int x1, int x2, int x3, int x4, int x5, int x6,
                               int x7, int x8, int x9, int x10){
        return x1+x2+x3+x4+x5+x6+x7+x8+x9+x10;
    }

    public static int sumOfTenWithLocal(int x1, int x2, int x3, int x4, int x5, int x6,
                               int x7, int x8, int x9, int x10){
        int sum = x1+x2+x3+x4+x5+x6+x7+x8+x9+x10;
        return sum;
    }

    public static void main(String[] args){
        SimpleMethods obj = new SimpleMethods(10,20);
        //obj.setX(obj.getX()+5);
        //obj.setY(obj.getY()+10);
        //obj.getX();
        two();
        SimpleMethods.number = 2;
        getNumber();
        obj.getAverage();
        System.out.println(obj.getX());
        callEmpty();
        double sum = SimpleMethods.sumOfTwo(10,10.1);
        System.out.println(sum);
        //obj.setY(obj.getY()+30);
        System.out.println(SimpleMethods.sumOfTen(1,2,3,4,5,6,7,8,9,10));
        System.out.println(SimpleMethods.sumOfTenWithLocal(1,2,3,4,5,6,7,8,9,10));
    }

}