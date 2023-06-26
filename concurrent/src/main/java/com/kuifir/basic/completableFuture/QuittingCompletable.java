package com.kuifir.basic.completableFuture;

import com.kuifir.basic.quit.QuittableTask;
import com.kuifir.basic.quit.QuittingTasks;
import com.kuifir.basic.task.Nap;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * 将{@link QuittingTasks}改用{@link CompletableFuture}来实现。
 * 本例中需要注意一个重点：并不要求用{@link java.util.concurrent.ExecutorService}来运行任务。
 * 这是由{@link CompletableFuture}来管理的（虽然可以选择实现自定义的ExecutorService）。
 * 你也无须调用shutdown(),事实上除非显式调用{@link CompletableFuture#join()},否则程序会在第一时间退出，而不会等待任务的完成。
 *
 * @author kuifir
 * @date 2023/6/25 22:26
 */
public class QuittingCompletable {
    public static void main(String[] args) {
        List<QuittableTask> tasks = IntStream.range(1, QuittingTasks.COUNT)
                .mapToObj(QuittableTask::new)
                .toList();
        List<CompletableFuture<Void>> cfutures = tasks.stream()
                .map(CompletableFuture::runAsync)
                .toList();
        new Nap(1);
        tasks.forEach(QuittableTask::quit);
        cfutures.forEach(CompletableFuture::join);
    }
}
