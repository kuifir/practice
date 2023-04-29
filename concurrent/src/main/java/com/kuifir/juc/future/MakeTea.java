package com.kuifir.juc.future;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * 用两个线程 T1 和 T2 来完成烧水泡茶程序，
 * T1 负责洗水壶、烧开水、泡茶这三道工序，T2 负责洗茶壶、洗茶杯、拿茶叶三道工序，
 * 其中 T1 在执行泡茶这道工序时需要等待 T2 完成拿茶叶的工序
 */
public class MakeTea {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        doWithFutureTask();
        doWithCompletableFuture();
    }

    private static void doWithCompletableFuture() throws ExecutionException, InterruptedException {
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> f1 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("T1:洗水壶...");
                    sleep(1);

                    System.out.println("T1:烧开水...");
                    sleep(15);
                });
        //任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> {
                    System.out.println("T2:洗茶壶...");
                    sleep(1);

                    System.out.println("T2:洗茶杯...");
                    sleep(2);

                    System.out.println("T2:拿茶叶...");
                    sleep(1);
                    return "龙井";
                });
        //任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<String> f3 =
                f1.thenCombine(f2, (__, tf) -> {
                    System.out.println("T1:拿到茶叶:" + tf);
                    System.out.println("T1:泡茶...");
                    return "上茶:" + tf;
                });
        //等待任务3执行结果
        System.out.println(f3.join());

    }

    private static void sleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void doWithFutureTask() throws ExecutionException, InterruptedException {
        // 创建任务T2的FutureTask
        FutureTask<String> ft2
                = new FutureTask<>(new T2Task());
        // 创建任务T1的FutureTask
        FutureTask<String> ft1
                = new FutureTask<>(new T1Task(ft2));
        // 线程T1执行任务ft1
        Thread T1 = new Thread(ft1);
        T1.start();
        // 线程T2执行任务ft2
        Thread T2 = new Thread(ft2);
        T2.start();
        // 等待线程T1执行结果
        System.out.println(ft1.get());
    }

    // T1Task需要执行的任务：
    // 洗水壶、烧开水、泡茶
    static class T1Task implements Callable<String> {
        FutureTask<String> ft2;

        // T1任务需要T2任务的FutureTask
        T1Task(FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1:洗水壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1:烧开水...");
            TimeUnit.SECONDS.sleep(15);
            // 获取T2线程的茶叶
            String tf = ft2.get();
            System.out.println("T1:拿到茶叶:" + tf);

            System.out.println("T1:泡茶...");
            return "上茶:" + tf;
        }
    }

    // T2Task需要执行的任务:
    // 洗茶壶、洗茶杯、拿茶叶
    static class T2Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("T2:洗茶壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2:洗茶杯...");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2:拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "龙井";
        }
    }
}
