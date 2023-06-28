package com.kuifir.basic.deadLock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * {@link StickHolder}筷子持有者类将一个{@link Chopstick}筷子类保存在一个长度为1的BlockingQueue中。以进行管理。
 * {@link BlockingQueue}是一种线程安全的集合，专门用于并发程序，如果调用take()却队列为空，它就会阻塞(等待)，
 * 一旦新的元素被放入队列，阻塞就会被解除，并返回该元素值。
 * 简单起见，Chopstick不会由StickHolder实际生成，而仅在类中是私有的。
 * 如果你调用pickUp()且筷子当前不可用，pickUp()会一直阻塞，知道筷子被其他Philosopher通过调用putDown()被返回。
 * 注意，本类中所有的线程安全性都是由BlockingQueue来保证的。
 * @author kuifir
 * @date 2023/6/28 22:08
 */
public class StickHolder {
    private static class Chopstick{}
    private Chopstick stick = new Chopstick();
    private BlockingQueue<Chopstick> holder = new ArrayBlockingQueue<>(1);
    public StickHolder(){putDown();}
    public void pickUp(){
        try {
            // 不可用时会阻塞
            holder.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void putDown(){
        try {
            holder.put(stick);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
