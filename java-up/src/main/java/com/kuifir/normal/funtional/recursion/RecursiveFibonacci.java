package com.kuifir.normal.funtional.recursion;

/**
 * 递归的lambda表达式：赋值实例变量
 * 用一个递归的lambda表达式来实现斐波那契函数，使用的实例变量，用构造器来初始化
 * @see IntCall
 * @author kuifir
 * @date 2023/5/10 23:45
 */
public class RecursiveFibonacci {
    IntCall fib;

    public RecursiveFibonacci() {
        this.fib = n -> n == 0 ? 0 :
                n == 1 ? 1 :
                        fib.call(n - 1) + fib.call(n - 2);
    }

    int fibonacci(int n) {
        return fib.call(n);
    }

    public static void main(String[] args) {
        RecursiveFibonacci recursiveFibonacci = new RecursiveFibonacci();
        for (int i = 0; i < 10; i++) {
            System.out.println(recursiveFibonacci.fibonacci(i));
        }
    }
}
