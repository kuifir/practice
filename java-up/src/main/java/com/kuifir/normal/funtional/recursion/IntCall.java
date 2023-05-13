package com.kuifir.normal.funtional.recursion;

/**
 * 在 Java中也可以编写递归的lambda表达式，但是有一点要注意：
 * 这个lambda表达式必须赋值给一个静态变量{@link RecursiveFactorial}或者一个实例变量{@link RecursiveFibonacci}，否则会出现编译错误
 * @author kuifir
 * @date 2023/5/10 23:44
 */
public interface IntCall {
    int call(int arg);
}
