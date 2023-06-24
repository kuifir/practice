package com.kuifir.basic.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 最简单的方式执行任务
 * @author kuifir
 * @date 2023/6/24 22:03
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
        // 并不存在SingleThreadExecutor类。newSingleThreadExecutor()是Executors中的工厂方法，用于创建特定类型的ExecutorService。
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0,10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        System.out.println("All tasks submitted");
        exec.shutdown();
        while (!exec.isTerminated()){
            System.out.println(Thread.currentThread().getName()+ " awaiting termination");
            new Nap(0.1);
        }
    }
}
