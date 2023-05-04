package com.kuifir.normal.stream.optional;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class OptionalMap {
    public static void main(String[] args) {
        // 如果Optional 不为empty,在将其传给函数时，map()首先会提取Optional中的对象
        test("basic", s->s);
        test("Add brackets", s -> "[" + s + "]");
        test("Increment", s -> {
            try {
                return Integer.parseInt(s) + 1 + "";
            } catch (Exception e) {
                return s;
            }
        });
        test("Replace", s -> s.replace("2", "9"));
        test("Take last digit", s -> s.length() > 0 ? s.charAt(s.length() - 1) + "" : s);
        // 在函数完成后，map()会先把结果包在一个Optional中，然后返回。遇到Optional.empty会直接通过，不在其上应用映射函数
    }

    static String[] elements = {"12", "", "23", "45"};

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String descr, Function<String, String> func) {
        System.out.println("-----(" + descr + ")---------");
        for (int i = 0; i <= elements.length; i++) { // 多出一个元素返回empty
            System.out.println(testStream()
                    .skip(i)
                    .findFirst() // 生成一个Optional
                    .map(func));
        }
    }
}
