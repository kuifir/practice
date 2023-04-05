package com.kuifir.future;


public class TaskServiceImpl<T, P> implements TaskService<T, P> {
    /**
     * 提交任务实现方法，不需要返回执行结果
     */
    @Override
    public Future<?> submit(Runnable runnable) {
        new Thread(runnable, Thread.currentThread().getName()).start();
        return new FutureTask<Void>();
    }

    /**
     * 提交任务实现方法，需要返回执行结果
     */
    @Override
    public Future<?> submit(Task<T, P> task, P param) {
        FutureTask<T> future = new FutureTask<>();
        new Thread(() -> {
            T result = task.doTask(param);
            future.finish(result);
        }, Thread.currentThread().getName()).start();
        return future;
    }
}
