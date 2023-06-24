package com.kuifir.basic.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 使用更多的线程：
 * 使用多线程的主要目的（几乎）总是使任务完成的更快一些，所以我们为何要将自己限制在SingleThreadExecutor中呢？
 * 看看Javadoc中的{@link java.util.concurrent.Executors}部分，
 * 你会发现更多选择，比如CachedThreadPool
 *
 * @author kuifir
 * @date 2023/6/24 22:21
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        IntStream.range(0,10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
}
