package com.kuifir.practice.semaphore;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SemaphoreDemo {
    public static void main(String[] args) {
        List<CompletableFuture<Void>> safeCompletableFutures =
                IntStream.range(0, 1000)
                        .mapToObj(i -> CompletableFuture.runAsync(SemaphoreDemo::addOne))
                        .collect(Collectors.toList());
        safeCompletableFutures.forEach(CompletableFuture::join);
        System.out.println(count);
        List<CompletableFuture<Void>> unSafeCompletableFutures
                = IntStream.range(0, 1000).mapToObj(i -> CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count += 1;
        })).collect(Collectors.toList());
        unSafeCompletableFutures.forEach(CompletableFuture::join);
        System.out.println(count);
    }

    private static int count = 0;
    // 初始化信号量
    private static final Semaphore semaphore = new Semaphore(1);

    static void addOne() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(2);
            count += 1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

}
