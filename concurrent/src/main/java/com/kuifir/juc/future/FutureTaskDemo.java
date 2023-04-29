package com.kuifir.juc.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureTask 实现了 Runnable 和 Future 接口，
 * 由于实现了 Runnable 接口，所以可以将 FutureTask 对象作为任务提交给 ThreadPoolExecutor 去执行，
 * 也可以直接被 Thread 执行；又因为实现了 Future 接口，所以也能用来获得任务的执行结果。
 */
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建FutureTask
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1 + 2);
        // 创建线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 提交FutureTask
        es.submit(futureTask);
        // 获取计算结果
        Integer result = futureTask.get();
        System.out.println(result);

        // 创建并启动线程
        Thread T1 = new Thread(futureTask);
        T1.start();
        // 获取计算结果
        System.out.println(futureTask.get());
    }
}
