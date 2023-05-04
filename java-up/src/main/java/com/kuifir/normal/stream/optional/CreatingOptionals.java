package com.kuifir.normal.stream.optional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 当需要自己编写生成Optional的代码是，有如下三种可以使用的静态方法
 * - empty(): 返回一个空的Optional
 * - of(value) : 如果已经知道这个value不是null,可以使用该方法将其包在一个Optional中
 * - ofNullable(value): 如果不知道这个value是不是null，使用这个方法。如果value为null，他会自动返回一个Optional.empty，否则会将这个value包在一个Optional中。
 */
public class CreatingOptionals {
    public static void main(String[] args) {
        test("empty",Optional.empty());
        test("of",Optional.of("Howdy"));
        try{
            test("of",Optional.of(null));
        }catch (Exception e){
            System.out.println(e);
        }
        test("ofNullable",Optional.ofNullable("Hi"));
        test("ofNullable",Optional.ofNullable(null));
    }
    static void test(String testName, Optional<String> opt) {
        System.out.println("=====" + testName + "======");
        System.out.println(opt.orElse("Null"));
    }

}
