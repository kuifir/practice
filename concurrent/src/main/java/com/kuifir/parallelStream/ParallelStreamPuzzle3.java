package com.kuifir.parallelStream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author kuifir
 * @date 2023/6/24 1:00
 */
public class ParallelStreamPuzzle3 {
    public static void main(String[] args) {
        List<Integer> collect = IntStream.range(0, 30)
                .peek(e -> System.out.println(e + ": " + Thread.currentThread().getName()))
                .limit(10)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
