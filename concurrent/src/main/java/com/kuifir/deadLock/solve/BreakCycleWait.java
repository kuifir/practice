package com.kuifir.deadLock.solve;

/**
 * 破坏循环等待条件
 * <p>
 * 破坏这个条件，需要对资源进行排序，然后按序申请资源。
 * 这个实现非常简单，我们假设每个账户都有不同的属性 id，
 * 这个 id 可以作为排序字段，申请的时候，我们可以按照从小到大的顺序来申请。
 * 比如下面代码中，①~⑥处的代码对转出账户（this）和转入账户（target）排序，然后按照序号从小到大的顺序锁定账户。这样就不存在“循环”等待了。
 */
public class BreakCycleWait {

    public static void main(String[] args) {
        Account a = new Account(1,100);
        Account b = new Account(2,100);
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
        private int id;
        private int balance;

        public Account(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        // 转账
        void transfer(Account target, int amt) {
            Account left = this;       //       ①
            Account right = target;    //       ②
            if (this.id > target.id) { //       ③
                left = target;         //       ④
                right = this;          //       ⑤
            }                          //       ⑥
            // 锁定序号小的账户
            synchronized (left) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 锁定序号大的账户
                synchronized (right) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        }
    }
}
