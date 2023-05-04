package com.kuifir.normal.stream.optional;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Optional的flatmap()被应用于` 已经会生成Optional的映射函数 `，所以flatmap()不会像map()那样包在Optional中
 */
public class OptionalFlatMap {
    public static void main(String[] args) {
        // 如果Optional 不为empty,在将其传给函数时，map()首先会提取Optional中的对象
        test("basic", Optional::of);
        test("Add brackets", s -> Optional.of("[" + s + "]"));
        test("Increment", s -> {
            try {
                return Optional.of(Integer.parseInt(s) + 1 + "");
            } catch (Exception e) {
                return Optional.of(s);
            }
        });
        test("Replace", s -> Optional.of(s.replace("2", "9")));
        test("Take last digit", s -> Optional.of(s.length() > 0 ? s.charAt(s.length() - 1) + "" : s));
    }

    static String[] elements = {"12", "", "23", "45"};

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String descr, Function<String, Optional<String>> func) {
        System.out.println("-----(" + descr + ")---------");
        for (int i = 0; i <= elements.length; i++) { // 多出一个元素返回empty
            System.out.println(testStream()
                    .skip(i)
                    .findFirst() // 生成一个Optional
                    .flatMap(func));
        }
    }
}
