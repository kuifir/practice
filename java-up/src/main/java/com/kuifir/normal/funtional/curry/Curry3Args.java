package com.kuifir.normal.funtional.curry;

import java.util.function.Function;

/**
 * @author kuifir
 * @date 2023/5/13 17:17
 */
public class Curry3Args {
    public static void main(String[] args) {
        Function<String,
                Function<String,
                        Function<String, String>>> sum =
                a -> b -> c -> a + b + c;
        Function<String, Function<String, String>> hi = sum.apply("Hi ");
        Function<String, String> ho = hi.apply("Ho ");
        String hup = ho.apply("Hup ");
        System.out.println(hup);
    }
}
