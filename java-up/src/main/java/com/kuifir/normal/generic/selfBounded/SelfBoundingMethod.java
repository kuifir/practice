package com.kuifir.normal.generic.selfBounded;

/**
 * 这使得该方法无法应用于除所示形式的自限定参数外的任何对象
 * @see SelfBounded
 * @author kuifir
 * @date 2023/5/28 17:28
 */
public class SelfBoundingMethod {
    static <T extends SelfBounded<T>> T f(T arg){
        return arg.set(arg).get();
    }

    public static void main(String[] args) {
        A a = f(new A());
    }
}
