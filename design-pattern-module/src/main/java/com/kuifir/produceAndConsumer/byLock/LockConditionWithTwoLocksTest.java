package com.kuifir.produceAndConsumer.byLock;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionWithTwoLocksTest {

    private final LinkedList<String> product = new LinkedList<>();
    // 如果直接获取队列的大小，可能读取错误的队列大小
    private AtomicInteger inventory = new AtomicInteger(0);//实时库存
    private int maxInventory = 10; // 最大库存
    private final Lock produceLock = new ReentrantLock();
    private final Lock consumerLock = new ReentrantLock();

    private final Condition notFullCondition = produceLock.newCondition();
    private final Condition notEmptyCondition = consumerLock.newCondition();

    public void produce(String element) {
        produceLock.lock();
        try {
            while (maxInventory == inventory.get()) {
                notFullCondition.await();
            }
            product.add(element);
            inventory.incrementAndGet();
            System.out.printf("放入一个商品库存: %s,总库存为：%d %n", element, inventory.get());
            if (inventory.get() < maxInventory) {
                notFullCondition.signalAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            produceLock.unlock();
        }
        if (inventory.get() > 0) {
            try {
                consumerLock.lockInterruptibly();
                notEmptyCondition.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                consumerLock.unlock();
            }
        }
    }

    public void consumer() {
        consumerLock.lock();
        try {
            while (0 == inventory.get()) {
                notEmptyCondition.await();
            }
            String pop = product.removeLast(); // pop会有异常，死锁？
            inventory.decrementAndGet();
            System.out.printf("消费一个商品库存: %s,总库存为：%d %n", pop, inventory.get());
            if (inventory.get() > 0) {
                notEmptyCondition.signalAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            consumerLock.unlock();
        }
        if (inventory.get() < maxInventory) {
            try {
                produceLock.lockInterruptibly();
                notFullCondition.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                produceLock.unlock();
            }
        }
    }

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
        LockConditionWithTwoLocksTest lc = new LockConditionWithTwoLocksTest();
        new Thread(lc.new Produce()).start();
        new Thread(lc.new Consumer()).start();
    }
}
