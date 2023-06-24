package com.kuifir.basic.task;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * 只有当所有的任务都完成时，invokeAll()才会返回由Future组成的List,
 * 每个Future都对应一个任务。
 * Future是Java5引入的机制，它允许你提交一个任务，并且不需要等待它完成。
 *
 * @see CountingTask
 * @author kuifir
 * @date 2023/6/24 22:53
 */
public class CachedThreadPool3 {
    public static Integer extractResult(Future<Integer> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<CountingTask> tasks = IntStream.range(0, 10)
                .mapToObj(CountingTask::new)
                .toList();
        // 只有当所有的任务都完成时，invokeAll()才会返回由Future组成的List,
        // 每个Future都对应一个任务。
        // Future是Java5引入的机制，它允许你提交一个任务，并且不需要等待它完成。
        List<Future<Integer>> futures = exec.invokeAll(tasks);
        Integer sum = futures.stream()
                .map(CachedThreadPool3::extractResult)
                .reduce(0, Integer::sum);
        System.out.println("sum= " + sum);
        exec.shutdown();

    }
}
