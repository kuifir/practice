package com.kuifir.basic.task;

import java.util.concurrent.*;

/**
 * 此处使用{@link java.util.concurrent.ExecutorService#submit(Callable)} )}
 * 当对尚未完成任务的Future调用get()方法时，调用会持续阻塞（等待），直到结果可用。
 * 这意味着{@link CachedThreadPool3}中，该Future有些冗余，因为在所有任务完成之前，invokeAll()甚至都不会返回、
 * 然而此处Future并不是用于延迟得到结果，而是为了捕获任何可能发生的异常。
 * 还要注意{@link CachedThreadPool3}中提取结果部分的混乱状况。get()会抛出异常，因此extractResult()是在Stream内部完成提取的。
 * 由于在调用get()时Future会阻塞，因此它只是将等待任务完成的问题推迟了。
 * 最终，Future被认为是一个无效的解决办法，现在人们并不鼓励使用它，而是更推荐Java8的{@link CompletableFuture}。
 *
 * @author kuifir
 * @date 2023/6/24 23:01
 * @see Callable
 * @see ExecutorService
 * @see Future
 */
public class Futures {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Integer> f = exec.submit(new CountingTask(99));
        System.out.println(f.get());
        exec.shutdown();
    }
}
