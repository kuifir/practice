package com.kuifir.future;

public interface TaskService<T, P> {
    Future<?> submit(Runnable runnable);//提交任务，不返回结果

    Future<?> submit(Task<T, P> task, P param);//提交任务，并返回结果
}
