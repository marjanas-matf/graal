package com.oracle.svm.test;

import org.junit.Test;

public class ReflectionTest {

    @Test
    public void test() {
        SimpleClass obj = instantiateClass(SimpleClass.class);
        Class objClass = obj.getClass();
        System.out.println("Class name: " + objClass.getName());
    }

    public static <T> T instantiateClass(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
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
}
