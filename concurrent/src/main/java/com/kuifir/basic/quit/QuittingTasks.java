package com.kuifir.basic.quit;

import com.kuifir.basic.task.Nap;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author kuifir
 * @date 2023/6/25 22:14
 */
public class QuittingTasks {
    public static final int COUNT = 150;

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        List<QuittableTask> tasks = IntStream.range(0, COUNT)
                .mapToObj(QuittableTask::new)
                .peek(es::execute)
                .toList();
        new Nap(1);
        tasks.forEach(QuittableTask::quit);
        es.shutdown();

    }
}
