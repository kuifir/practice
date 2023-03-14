package com.kuifir.produceAndConsumer.byLock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @decription 从代码中应该不难发现，生产者和消费者都在竞争同一把锁，而实际上两者没有同步关系，
 * 由于 Condition 能够支持多个等待队列以及不响应中断，
 * 所以我们可以将生产者和消费者的等待条件和锁资源分离，从而进一步优化系统并发性能
 */
public class LockConditionTest {

    private final LinkedList<String> product = new LinkedList<>();
    private final int maxInventory = 10; // 最大库存
    private final Lock lock = new ReentrantLock();// 资源锁
    private final Condition condition = lock.newCondition(); // 库存非满和非空条件

    /**
     * 新增商品
     *
     */
    public void produce(String e) {
        lock.lock();
        try {
            while (maxInventory == product.size()) {
                condition.await();
            }
            product.add(e);
            System.out.printf("放入一个商品库存: %s,总库存为：%d %n", e, product.size());
            condition.signalAll();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }


    }

    public void consumer() {
        lock.lock();
        try {
            while (product.size() == 0) {
                condition.await();
            }
            String pop = product.pop();
            System.out.printf("消费一个商品库存: %s,总库存为：%d %n", pop, product.size());
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 生产者
     */
    private class Produce implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                produce("商品" + i);
            }
        }
    }

    /**
     * 消费者
     */
    private class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                consumer();
            }
        }
    }

    public static void main(String[] args) {
        LockConditionTest lc = new LockConditionTest();
        new Thread(lc.new Produce()).start();
        new Thread(lc.new Consumer()).start();
        new Thread(lc.new Produce()).start();
        new Thread(lc.new Consumer()).start();
    }
}
