package com.kuifir.deadLock.solve;

import com.kuifir.deadLock.probem.DeadLockProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * 破坏占用且等待条件
 * 从理论上讲，要破坏这个条件，可以一次性申请所有资源
 * <p>
 * <p>
 * 用“等待-通知”机制优化循环等待{@link com.kuifir.deadLock.solve.DestroyPossessionAndWait}
 *
 * @see DestroyPossessionAndWait
 * @see DeadLockProblem
 */
public class DestroyPossessionAndWaitWithWaitNotify {
    private static final Allocator allocator = new Allocator();

    public static void main(String[] args) {
        Account a = new Account(allocator, 100);
        Account b = new Account(allocator, 100);
        Runnable runnable = () -> {
            a.transfer(b, 10);
        };
        Runnable runnable2 = () -> {
            b.transfer(a, 10);
        };
        new Thread(runnable).start();
        new Thread(runnable2).start();
    }

    static class Allocator {
        private final List<Object> als = new ArrayList<>();

        // 一次性申请所有资源
        synchronized boolean apply(Object from, Object to) {
            while (als.contains(from) || als.contains(to)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            als.add(from);
            als.add(to);
            return true;
        }

        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
            this.notifyAll();
        }

        private Allocator() {

        }
    }

    static class Account {
        private final Allocator allocator;
        private int balance;

        public Account(Allocator allocator) {
            this.allocator = allocator;
        }

        Account(Allocator allocator, int balance) {
            this.allocator = allocator;
            this.balance = balance;
        }

        // 转账
        void transfer(Account target, int amt) {
            // 一次性申请转出账户和转入账户，直到成功
            while (!allocator.apply(this, target)) ;
            try {
                // 锁定转出账户
                synchronized (this) {
                    Thread.sleep(1000L);
                    // 锁定转入账户
                    synchronized (target) {
                        if (this.balance > amt) {
                            this.balance -= amt;
                            target.balance += amt;
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                allocator.free(this, target);
            }
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }
}
