package com.kuifir.normal.stream.optional;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 有很多便捷函数，可以用于获取Optional中的数据，他们 "简化了先检查在处理所包含对象" 的过程:
 * - ifPresent(Consumer): 如果值存在，则用这个值来调用Consumer,否则什么都不做
 * - orElse(otherObject): 如果对象存在，则返回这个对象，否则返回otherObject
 * - orElseGet(Supplier): 如果对象存在，则返回这个对象。否则返回使用Supplier函数创建的替代对象
 * - orElseThrow(Supplier): 如果对象存在，则返回这个对象。否则抛出一个能使用Supplier函数创建的异常
 */
public class Optionals {
    public static void main(String[] args) {
        test("basic",Optionals::basics);
        test("ifPresent",Optionals::ifPresent);
        test("orElse",Optionals::orElse);
        test("orElseGet",Optionals::orElseGet);
        test("orElseThrow",Optionals::orElseThrow);
    }
    static void test(String testName, Consumer<Optional<String>> cos) {
        System.out.println("=====" + testName + "======");
        cos.accept(Stream.of("Epithets").findFirst());
        cos.accept(Stream.<String>empty().findFirst());
    }

    static void basics(Optional<String> optString) {
        if (optString.isPresent()) {
            System.out.println(optString.get());
        } else {
            System.out.println("Nothing inside!");
        }
    }

    static void ifPresent(Optional<String> optString) {
        optString.ifPresent(System.out::println);
    }

    static void orElse(Optional<String> optString) {
        System.out.println(optString.orElse("Nada"));
    }

    static void orElseGet(Optional<String> optString) {
        System.out.println(optString.orElseGet(() -> "Generated"));
    }

    static void orElseThrow(Optional<String> optString) {
        try {
            System.out.println(optString.orElseThrow(() -> new Exception("Supplied")));
        } catch (Exception e) {
            System.out.println("Caught" + e);
        }
    }


}
