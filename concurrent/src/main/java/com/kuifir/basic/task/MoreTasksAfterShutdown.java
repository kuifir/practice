package com.kuifir.basic.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 一旦调用了{@link ExecutorService#shutdown()},
 * 此后在想提交新任务，就会抛出{@link java.util.concurrent.RejectedExecutionException}异常
 * <p></p>
 * {@link ExecutorService#shutdown()}的性地方法是{@link {@link ExecutorService#shutdownNow()},
 * 其作用是不再接受新任务，同时还会尝试通过中断来停止所有正在运行的任务。
 * 再次提醒，中断线程容易引发混乱和错误，并不鼓励这么做。
 * @see ExecutorService
 * @see java.util.concurrent.RejectedExecutionException
 * @author kuifir
 * @date 2023/6/24 22:14
 */
public class MoreTasksAfterShutdown {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new NapTask(1));
        exec.shutdown();
        try {
            exec.execute(new NapTask(99));
        }catch (RejectedExecutionException e){
            System.out.println(e);
        }
    }

}
