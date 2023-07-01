package com.kuifir.juc.BlockingQueue.PriorityBlockingQueue;

import com.kuifir.basic.task.Nap;

import java.util.List;
import java.util.Queue;
import java.util.SplittableRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 优先级阻塞队列PriorityBlockingQueue
 * 这基本上是一种能够阻塞读取操作的优先队列。
 * 下面的示例中未Prioritized指定了优先级指数。一些Producer任务的实例将Prioritized对象插入PriorityBlockingQueue,
 * 不过在插入操作之间加入了随机的延迟。然后，单个Consumer任务在执行take()时会呈现出多个选项，PriorityBlockingQueue
 * 则将此时具有最高优先级的Prioritized对象传给它。
 *
 * @author kuifir
 * @date 2023/7/1 20:15
 */
public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        PriorityBlockingQueue<Prioritized> queue = new PriorityBlockingQueue<>();
        CompletableFuture.runAsync(new Producer(queue));
        CompletableFuture.runAsync(new Producer(queue));
        CompletableFuture.runAsync(new Producer(queue));
        CompletableFuture.runAsync(new Consumer(queue)).join();
    }
}

class Prioritized implements Comparable<Prioritized> {
    private static AtomicInteger counter = new AtomicInteger();
    private final int id = counter.getAndIncrement();
    private final int priority;
    private static List<Prioritized> sequence = new CopyOnWriteArrayList<>();

    public Prioritized(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(Prioritized o) {
        return Long.compare(o.priority, priority);
    }

    @Override
    public String toString() {
        return String.format("[%d] Prioritized %d", priority, id);
    }

    public void displaySequence() {
        int count = 0;
        for (Prioritized pt : sequence) {
            System.out.printf("(%d:%d)", pt.id, pt.priority);
            if (++count % 5 == 0) {
                System.out.println();
            }
        }
    }

    public static class EndSentinel extends Prioritized {

        public EndSentinel() {
            super(-1);
        }
    }
}

class Producer implements Runnable {
    private static AtomicInteger seed = new AtomicInteger(47);
    private SplittableRandom rand = new SplittableRandom(seed.getAndAdd(10));
    private Queue<Prioritized> queue;

    public Producer(Queue<Prioritized> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        rand.ints(10, 0, 20)
                .mapToObj(Prioritized::new)
                .peek(p -> new Nap(rand.nextDouble() / 10))
                .forEach(p -> queue.add(p));
        queue.add(new Prioritized.EndSentinel());
    }
}

class Consumer implements Runnable {
    private PriorityBlockingQueue<Prioritized> q;
    private SplittableRandom rand = new SplittableRandom(47);

    public Consumer(PriorityBlockingQueue<Prioritized> q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            Prioritized pt = null;
            try {
                pt = q.take();
                System.out.println(pt);
                if (pt instanceof Prioritized.EndSentinel) {
                    pt.displaySequence();
                    break;
                }
                new Nap(rand.nextDouble() / 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}