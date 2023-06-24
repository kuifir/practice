package com.kuifir.basic.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 结果（每次都不一样）
 * <pre>{@code
 * 7 pool-1-thread-8 704
 * 6 pool-1-thread-7 604
 * 3 pool-1-thread-4 304
 * 2 pool-1-thread-3 204
 * 9 pool-1-thread-10 904
 * 4 pool-1-thread-5 404
 * 8 pool-1-thread-9 804
 * 0 pool-1-thread-1 100
 * 5 pool-1-thread-6 504
 * 1 pool-1-thread-2 104}
 * </pre>
 * <p></p>
 * 输出结果并不如我们所料，并且每次运行结果都不一样。
 * 问题在于所有的任务都在试图对单例的val进行写操作，它们在相互打架。
 * 我们认为这样的类是非线程安全的。
 *
 * @author kuifir
 * @date 2023/6/24 22:31
 */
public class CachedThreadPool2 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(InterferingTask::new)
                .forEach(exec::execute);
        exec.shutdown();
        // 7 pool-1-thread-8 704
        // 6 pool-1-thread-7 604
        // 3 pool-1-thread-4 304
        // 2 pool-1-thread-3 204
        // 9 pool-1-thread-10 904
        // 4 pool-1-thread-5 404
        // 8 pool-1-thread-9 804
        // 0 pool-1-thread-1 100
        // 5 pool-1-thread-6 504
        // 1 pool-1-thread-2 104
    }


}
