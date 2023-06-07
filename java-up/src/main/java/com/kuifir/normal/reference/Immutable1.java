package com.kuifir.normal.reference;

/**
 * 创建不可变类
 * 不可变对象可以对引用别名的风险免疫
 *
 * @author kuifir
 * @date 2023/6/7 22:28
 */
public class Immutable1 {
    private final int data;

    public Immutable1(int initValue) {
        this.data = initValue;
    }

    public int read() {
        return data;
    }

    public boolean nonzero() {
        return data != 0;
    }

    public Immutable1 multiply(int multiplier) {
        return new Immutable1(data * multiplier);
    }

    public static void f(Immutable1 i1) {
        Immutable1 quad = i1.multiply(4);
        System.out.println(i1.read());
        System.out.println(quad.read());
    }

    public static void main(String[] args) {
        Immutable1 x = new Immutable1(47);
        System.out.println("x = " + x.read());
        f(x);
        System.out.println("x = " + x.read());
    }
}
