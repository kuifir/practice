package com.kuifir.normal.stream.optional;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 有三种方法支持对Optional进行时候处理，所以如果你的流管线生成了一个Optional，你可以在最后做一项处理。
 * <p>
 * - filter(Predicate): 将Predicate应用与Optional的内容，放返回其结果。如果Optional与Predicate不匹配，则将其转换为empty。如果Optional本身已经是empty,则直接传回
 * - map(Function) : 如果Optional不为empty ,则将Function应用于Optional包含的对象，并返回结果，否则返回Optional.empty。
 * - flatmap(Function): 和map()类似，但是所提供的映射函数会将结果在包在Optional中，这样flatmap()最后就不会再做任何包装了。
 * <p>
 * 数值化的Optional上没有提供这些操作
 */
public class OptionalFilter {
    public static void main(String[] args) {
        test("true", str -> true);
        test("false", str -> false);
        test("str !=\"\"", str -> str != "");
        test("str.length() ==3", str -> str.length() ==3);
        test("str.startsWith(\"B\")", str -> str.startsWith("B"));

    }

    static String[] elements = {
            "Foo", "", "Bar", "Baz", "Bingo"
    };

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String descr, Predicate<String> pred) {
        System.out.println("-----(" + descr + ")---------");
        for (int i = 0; i <= elements.length; i++) { // 多出一个元素返回empty
            System.out.println(testStream()
                    .skip(i)
                    .findFirst() // 生成一个Optional
                    .filter(pred));
        }
    }
}
