package com.kuifir.juc.BlockingQueue.DelayQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 延迟队列DelayQueue
 * 这是一种实现了{@link Delayed}接口的对象组成的无边界{@link BlockingQueue}(阻塞队列)。
 * 一个对象只有在其延迟时间到期后才能从队列中取出。队列是有序的，因此头部对象的延迟时间最长。
 * 如果没有到达延迟时间，那么头部元素就相当于不存在，同时poll()会返回null(因此不能将nul放入队列)。
 * <p></p>
 * 下面这个示例中，Delayed对象自身就是任务，而 DelayedTaskConsumer将最"紧急"的任务(即过期时间最长的任务)
 * 从队列中取出并执行.因此，要注意DelayQueue是优先级队列的变体。
 *
 * @author kuifir
 * @date 2023/7/1 19:47
 */
class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();

    DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public int compareTo(Delayed arg) {
        DelayedTask that = (DelayedTask) arg;
        return Long.compare(trigger, that.trigger);
    }

    @Override
    public void run() {
        System.out.println(this + "　");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public String toString() {
        return String.format("[%d] Task %d", delta, id);
    }

    public String summary() {
        return String.format("(%d:%d)", id, delta);
    }

    public static class EndTask extends DelayedTask {

        EndTask(int delay) {
            super(delay);
        }

        @Override
        public void run() {
            sequence.forEach(dt -> System.out.println(dt.summary()));
        }
    }
}

/**
 * @author myfir
 */
public class DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedTask> tasks = Stream.concat(
                // 随机延迟
                new Random(47).ints(20, 0, 4000)
                        .mapToObj(DelayedTask::new),
                // 增加总结任务
                Stream.of(new DelayedTask.EndTask(4000))
        ).collect(Collectors.toCollection(DelayQueue::new));
        while (tasks.size() > 0) {
            tasks.take().run();
        }
    }
}