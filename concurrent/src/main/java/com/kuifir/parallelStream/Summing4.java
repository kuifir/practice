package com.kuifir.parallelStream;

import java.util.Arrays;

/**
 * @author kuifir
 * @date 2023/6/23 23:37
 */
public class Summing4 {
    public static void main(String[] args) {
        Long[] aL = new Long[Summing3.SZ + 1];
        Arrays.parallelSetAll(aL, i -> (long) i);
        Summing.timeTest("Long Parallel", Summing3.CHECK, () -> Arrays.stream(aL)
                .parallel().reduce(0L, Long::sum));
    }
}
