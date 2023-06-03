package com.kuifir.normal.reference;

/**
 * 方法调用隐式地对其参数命名了引用别名
 * 在你将引用作为参数传入的时候-这正是Java的工作方式-你实际上已经自动命名引用别名了，
 * 因为本地创建的引用可以修改"外部的对象"（在方法作用域外创建的对象）
 * @author kuifir
 * @date 2023/6/3 22:49
 */
public class Alias2 {
    private int i;

    public Alias2(int i) {
        this.i = i;
    }
    public static void f(Alias2 reference){
        reference.i++;
    }

    public static void main(String[] args) {
        Alias2 x = new Alias2(7);
        System.out.println("x: " + x.i);
        System.out.println("Calling f(x)" );
        f(x);
        System.out.println("x: " + x.i);
    }
}
