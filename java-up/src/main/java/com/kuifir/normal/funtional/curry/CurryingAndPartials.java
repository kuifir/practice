package com.kuifir.normal.funtional.curry;

import java.util.function.Function;

/**
 * 柯里化
 * 柯里化（Curring）是以其发明者之一的Haskell Curry 的姓氏命名的。
 * 而 Haskell Curry也可能是唯一一位姓氏和名字都被用来命名重要事物的计算机科学家。
 * Haskell 编程语言就是以他的名字命名的。
 * 柯里化的意思是，将一个接受多个参数的函数转变为一系列只接受一个参数的函数。
 *<p/>
 * 柯里化的目的是能够通过提供一个参数来创建一个新函数，
 * 所以我们现在有了一个"参数化函数"和剩下的"自由参数"。
 * 实际上，我们从一个接受两个参数的函数开始，最后变成了一个单参数的函数
 * <p/>
 * 还可以再添加一层，对接受三个参数的函数进行柯里化{@link Curry3Args}
 * @author kuifir
 * @date 2023/5/13 17:00
 */
public class CurryingAndPartials {
    /**
     * 未柯里化：
     */
    static String unCurried(String a, String b) {
        return a + b;
    }

    public static void main(String[] args) {
        // 未柯里化
        System.out.println(unCurried("Hi", "Ho"));
        // 柯里化函数：f(a), g(b) -> g(f(x))
        Function<String, Function<String, String>> sum =
                a ->
                        b ->
                                a + b;
        System.out.println(sum.apply("a").apply("b"));
        // g(b)
        Function<String, String> hi = sum.apply("Hi");
        System.out.println(hi.apply("Ho"));

        // 部分应用
        Function<String, String> sumHi = sum.apply("Hup");
        System.out.println(sumHi.apply("Ho"));
        System.out.println(sumHi.apply("Hey"));
    }
}
