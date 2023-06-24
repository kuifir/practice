package com.kuifir.basic.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 如果仅仅调用exec.shutdown()，程序会在所有任务完成后立即结束。也就是说while(!exec.isTerminated())并不是必须的，
 * @author kuifir
 * @date 2023/6/24 22:11
 */
public class SingleThreadExecutor2 {
    public static void main(String[] args) {
        // 并不存在SingleThreadExecutor类。newSingleThreadExecutor()是Executors中的工厂方法，用于创建特定类型的ExecutorService。
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0,10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        System.out.println("All tasks submitted");
        exec.shutdown();
    }

}
