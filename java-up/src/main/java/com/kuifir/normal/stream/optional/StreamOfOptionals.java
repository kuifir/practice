package com.kuifir.normal.stream.optional;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 使用Optional流时，我们必须弄清如何获得Optional中的对象
 */
public class StreamOfOptionals {
    public static void main(String[] args) {
        Signal.stream().limit(10).forEach(System.out::println);
        System.out.println("-----");
        Signal.stream().limit(10).filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println);
    }
}
