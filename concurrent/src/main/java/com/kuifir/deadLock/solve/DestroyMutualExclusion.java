package com.kuifir.deadLock.solve;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 破坏不能抢占
 * <p>
 * 这个方案 synchronized 没有办法解决。
 * 原因是 synchronized 申请资源的时候，如果申请不到，线程直接进入阻塞状态了，
 * 而线程进入阻塞状态，啥都干不了，也释放不了线程已经占有的资源。
 * <p>
 * 但我们希望的是：
 * 对于“不可抢占”这个条件，占用部分资源的线程进一步申请其他资源时,
 * 如果申请不到，可以主动释放它占有的资源，这样不可抢占这个条件就破坏掉了。
 * <p>
 * 如果我们重新设计一把互斥锁去解决这个问题，那该怎么设计呢？
 * 我觉得有三种方案。
 * 能够响应中断。synchronized 的问题是，持有锁 A 后，如果尝试获取锁 B 失败，那么线程就进入阻塞状态，一旦发生死锁，就没有任何机会来唤醒阻塞的线程。但如果阻塞状态的线程能够响应中断信号，也就是说当我们给阻塞的线程发送中断信号的时候，能够唤醒它，那它就有机会释放曾经持有的锁 A。这样就破坏了不可抢占条件了。
 * 支持超时。如果线程在一段时间之内没有获取到锁，不是进入阻塞状态，而是返回一个错误，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。
 * 非阻塞地获取锁。如果尝试获取锁失败，并不进入阻塞状态，而是直接返回，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。
 * 这三种方案可以全面弥补 synchronized 的问题。
 * 到这里相信你应该也能理解了，这三个方案就是“重复造轮子”的主要原因，体现在 API 上，就是 Lock 接口的三个方法。
 * 详情如下：
 * // 支持中断的API void lockInterruptibly() throws InterruptedException;
 * // 支持超时的API boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
 * // 支持非阻塞获取锁的API boolean tryLock();
 * <p>
 * 如何保证可见性Java SDK 里面 Lock 的使用，
 * 有一个经典的范例，就是try{}finally{}，需要重点关注的是在 finally 里面释放锁。
 * 这个范例无需多解释，你看一下下面的代码就明白了。
 * 但是有一点需要解释一下，那就是可见性是怎么保证的。
 * 你已经知道 Java 里多线程的可见性是通过 Happens-Before 规则保证的，
 * 而 synchronized 之所以能够保证可见性，
 * 也是因为有一条 synchronized 相关的规则：synchronized 的解锁 Happens-Before 于后续对这个锁的加锁。
 * 那 Java SDK 里面 Lock 靠什么保证可见性呢？
 * 例如在下面的代码中，线程 T1 对 value 进行了 +=1 操作，那后续的线程 T2 能够看到 value 的正确结果吗？
 * <p>
 * class X {
 * private final Lock rtl =
 * new ReentrantLock();
 * int value;
 * public void addOne() {
 * // 获取锁
 * rtl.lock();
 * try {
 * value+=1;
 * } finally {
 * // 保证锁能释放
 * rtl.unlock();
 * }
 * }
 * }
 * <p>
 *
 * @see Lock#lockInterruptibly()
 * @see Lock#tryLock(long, TimeUnit)
 * @see Lock#tryLock()
 */
public class DestroyMutualExclusion {
    public static void main(String[] args) {
        Account a = new Account(100);
        Account b = new Account(100);
        Runnable runnable = () -> {
            try {
                a.transfer(b, 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable runnable2 = () -> {
            try {
                b.transfer(a, 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(runnable).start();
        new Thread(runnable2).start();
    }


    static class Account {
        private int balance;
        private final Lock lock = new ReentrantLock();


        Account(int balance) {
            this.balance = balance;
        }

        // 转账
        void transfer(Account target, int amt) throws InterruptedException {
            // 一次性申请转出账户和转入账户，直到成功
            SplittableRandom random = new SplittableRandom();
            while (true) {
                    // 不加随机时间会活锁
                if (this.lock.tryLock(random.nextLong(1000), TimeUnit.MILLISECONDS)) {
                    System.out.format("%s ：获取{%s}的锁%n", Thread.currentThread().getName(), this);
                    try {
                        // 锁定转出账户
                        Thread.sleep(1000L);
                        // 锁定转入账户
                        //  不加随机时间会活锁
                        if (target.lock.tryLock(random.nextLong(1000), TimeUnit.MICROSECONDS)) {
                            System.out.format("%s ： 获取{%s}的锁%n", Thread.currentThread().getName(), target);
                            try {
                                if (this.balance > amt) {
                                    this.balance -= amt;
                                    target.balance += amt;
                                    return;
                                }
                            } finally {
                                target.lock.unlock();
                            }

                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        this.lock.unlock();
                    }
                }
            }
        }
    }
}
