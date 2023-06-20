package com.kuifir.juc.completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        List<Integer> nums = new ArrayList<>();
        List<CompletableFuture<Integer>> completableFutures = IntStream.range(1, 10)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 3) {
                        throw new RuntimeException("有一个异常");
                    }
                    System.out.println(i);
                    return i;
                })).toList();

        try {
            for (int i = 0; i < 5; i++) {
                nums.add(completableFutures.get(i).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new Exception("捕获到异常");
        }
        System.out.println(nums);
    }
}
