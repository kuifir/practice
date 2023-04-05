package com.kuifir.practice.semaphore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreDemo {
    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0,1000).parallel().forEach(i ->{
            new Thread(SemaphoreDemo::addOne).start();
        });
        Thread.sleep(3000);
        System.out.println(count);
        IntStream.range(0,1000).parallel().forEach(i ->{
            new Thread(() -> {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count += 1;
            }).start();
        });
        Thread.sleep(10000);
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
            count +=1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

}
