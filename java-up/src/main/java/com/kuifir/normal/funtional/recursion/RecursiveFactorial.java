package com.kuifir.normal.funtional.recursion;

/**
 * 递归的lambda表达式：赋值静态变量:
 * 用一个递归的lambda表达式来实现递归实现的阶乘
 *
 * @author kuifir
 * @date 2023/5/13 14:55
 * @see IntCall
 */
public class RecursiveFactorial {
    /**
     * 不在定义的时候来初始化fact
     * 下面这样会发生编译错误
     * <pre>{@code
     *   static IntCall fact = n -> n == 0 ? 0 : n == 1 ? 1 : n * fact.call(n - 1)
     * }
     *
     * </pre>
     */
    static IntCall fact;

    public static void main(String[] args) {
        fact = n -> n == 0 ? 0 : n == 1 ? 1 : n * fact.call(n - 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(fact.call(i));
        }
    }
}
