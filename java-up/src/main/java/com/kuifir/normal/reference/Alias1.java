package com.kuifir.normal.reference;

/**
 * 引用别名
 * 引用别名指的是不止一个引用被绑定到了同一个对象上的情况。
 * 别名导致的问题主要发生在有人对对象进行 "写" 操作时。
 * 如果该对象的其他引用持有者并不希望对象发生变更，那么结果将使其大感意外。
 *
 * @author kuifir
 * @date 2023/6/3 22:41
 */
public class Alias1 {
    private int i;

    public Alias1(int i) {
        this.i = i;
    }

    public static void main(String[] args) {
        Alias1 x = new Alias1(7);
        // 分配引用
        Alias1 y = x;
        System.out.println("x: " + x.i);
        System.out.println("y: " + y.i);
        System.out.println("Incrementing x");
        x.i++;
        System.out.println("x: " + x.i);
        System.out.println("y: " + y.i);
    }
}
