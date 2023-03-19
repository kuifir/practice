package com.kuifir.deadLock.solve;

import java.util.ArrayList;
import java.util.List;

/**
 * 破坏占用且等待条件
 * 从理论上讲，要破坏这个条件，可以一次性申请所有资源
 * <p>
 * “同时申请”这个操作是一个临界区，我们也需要一个角色（Java 里面的类）来管理这个临界区，我们就把这个角色定为 Allocator。
 * 它有两个重要功能，分别是：同时申请资源 apply() 和同时释放资源 free()。
 * 账户 Account 类里面持有一个 Allocator 的单例（必须是单例，只能由一个人来分配资源）。
 * 当账户 Account 在执行转账操作的时候，首先向 Allocator 同时申请转出账户和转入账户这两个资源，成功后再锁定这两个资源；
 * 当转账操作执行完，释放锁之后，我们需通知 Allocator 同时释放转出账户和转入账户这两个资源。
 */
public class DestroyMutualExclusion {
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
            if (als.contains(from) || als.contains(to)) {
                return false;
            } else {
                als.add(from);
                als.add(to);
            }
            return true;
        }

        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
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
