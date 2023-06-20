package com.kuifir.juc.lock.blockQueue;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class BlockedQueueByLockAndCondition<T> {
    public static void main(String[] args) {
        BlockedQueueByLockAndCondition<Integer> blockedQueueByLockAndCondition = new BlockedQueueByLockAndCondition();
        new Thread(() -> {
            IntStream.range(1, 100).forEach(i -> {
                new Thread(() -> {
                    System.out.println(i + "入队");
                    blockedQueueByLockAndCondition.enq(i);
                }).start();
            });

        }).start();
        new Thread(() -> {
            IntStream.range(1, 100).forEach(i -> {
                new Thread(() -> {
                    Integer deq = blockedQueueByLockAndCondition.deq();
                    System.out.println(deq + "出队");
                }).start();
            });

        }).start();
    }

    private final LinkedList<T> queue = new LinkedList<>(new ArrayList<>(10));
    final Lock lock =
            new ReentrantLock();
    // 条件变量：队列不满
    final Condition notFull =
            lock.newCondition();
    // 条件变量：队列不空
    final Condition notEmpty =
            lock.newCondition();

    // 入队
    void enq(T x) {
        lock.lock();
        try {
            while (queue.size() == 10) {
                // 等待队列不满
                notFull.await();
            }
            // 入队操作...
            queue.addLast(x);
            //入队后,通知可出队
            notEmpty.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    // 出队
    T deq() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                // 等待队列不空
                notEmpty.await();
            }
            // 出队操作...
            T first = queue.getFirst();
            queue.removeFirst();
            //出队后，通知可入队
            notFull.signal();
            return first;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
