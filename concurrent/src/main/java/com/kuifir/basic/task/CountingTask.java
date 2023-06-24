package com.kuifir.basic.task;

import java.util.concurrent.Callable;

/**
 * 由于InterferingTask 是一个Runnable的实现，他并没有返回值，因此只能通过副作用生成结果，
 * 即通过控制环境来生成（而不是直接返回）结果。
 * 副作用是并发编程的主要问题之一，正如在{@link CachedThreadPool2}中所见、
 * {@link InterferingTask}中的val称作<b>可变共享状态</b>（mutable shared state）,
 * 正是它带来了问题：多个任务同时修改同一个变量会导致所谓的竞态条件。
 * 结果由哪个任务抢先得到终点并修改了变量（以及其他各种可能性）而决定。
 * <p></p>
 * 避免竞态条件的最好方法是避免使用可变共享状态。
 * 我们可以称其为<b>自私儿童原则</b>（selfish child principle）:什么都不共享
 * {@link CountingTask#call()}完全独立的生成结果，独立于其他的CountingTask，
 * 这意味着并不会存在可变共享状态。
 * ExecutorService允许在集合中通过invokeAll()来启动所有的Callable:{@link CachedThreadPool3}
 * @author kuifir
 * @date 2023/6/24 22:41
 */
public class CountingTask implements Callable<Integer> {
    final int id;

    public CountingTask(int id) {
        this.id = id;
    }

    @Override
    public Integer call() {
        Integer val = 0;
        for (int i = 0; i < 100; i++) {
            val++;
        }
        System.out.println(id + " " + Thread.currentThread().getName() + " " + val);
        return val;
    }
}
