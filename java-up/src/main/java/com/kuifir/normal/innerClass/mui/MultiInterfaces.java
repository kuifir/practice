package com.kuifir.normal.innerClass.mui;

/**
 * 一个类可以以两种方式实现多个接口：
 * - 一个单独的类实现多个接口
 * - 使用内部类
 * @author kuifir
 * @date 2023/5/7 18:09
 */
interface A {
}

interface B {
}

class X implements A, B {
}

class Y implements A {
    B makeB() {
        // 匿名内部类
        return new B() {
        };
    }
}

/**
 * @author myfir
 */
public class MultiInterfaces {
    static void takesA(A a) {
    }

    static void takesB(B b) {
    }

    public static void main(String[] args) {
        X x = new X();
        Y y = new Y();
        takesA(x);
        takesA(y);

        takesA(x);
        takesB(y.makeB());

    }
}
