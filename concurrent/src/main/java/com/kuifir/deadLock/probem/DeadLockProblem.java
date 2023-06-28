package com.kuifir.deadLock.probem;

/**
 * 只有以下这四个条件都发生时才会出现死锁：
 * <p>
 * 1.互斥，共享资源 X 和 Y 只能被一个线程占用；
 * 2.占有且等待，线程 T1 已经取得共享资源 X，在等待共享资源 Y 的时候，不释放共享资源 X；
 * 3.不可抢占，其他线程不能强行抢占线程 T1 占有的资源；
 * 4.循环等待，线程 T1 等待线程 T2 占有的资源，线程 T2 等待线程 T1 占有的资源，就是循环等待。
 * <p>
 * 其中，互斥这个条件我们没有办法破坏，因为我们用锁为的就是互斥。不过其他三个条件都是有办法破坏掉的
 *
 * @author kuifir
 */
public class DeadLockProblem {

    public static void main(String[] args) {
        Account a = new Account();
        a.setBalance(100);
        Account b = new Account();
        b.setBalance(100);
        Runnable runnable = () -> {
            a.transfer(b, 10);
        };
        Runnable runnable2 = () -> {
            b.transfer(a, 10);
        };
        new Thread(runnable).start();
        new Thread(runnable2).start();
    }

    static class Account {
        private int balance;

        // 转账
        void transfer(Account target, int amt) {
            // 锁定转出账户
            System.out.format("%s : 准备获取{%s}的锁%n", Thread.currentThread().getName(), this);
            synchronized (this) {
                System.out.format("%s : 获取{%s}的锁%n", Thread.currentThread().getName(), this);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 锁定转入账户
                System.out.format("%s : 准备获取{%s}的锁%n", Thread.currentThread().getName(), target);
                synchronized (target) {
                    System.out.format("%s　:　获取{%s}的锁%n", Thread.currentThread().getName(), target);
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
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
