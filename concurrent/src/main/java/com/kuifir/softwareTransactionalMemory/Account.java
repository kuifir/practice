package com.kuifir.softwareTransactionalMemory;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * useCase
 *
 * @author kuifir
 * @date 2023/5/22 22:48
 */
public class Account {
    /**
     * 余额
     */
    private TxnRef<Integer> balance;

    /**
     * 构造方法
     */
    public Account(int balance) {
        this.balance = new TxnRef<>(balance);
    }

    /**
     * 转账操作
     */
    public void transfer(Account target, int amt) {
        STM.atomic((txn) -> {
            Integer from = balance.getValue(txn);
            balance.setValue(from - amt, txn);
            Integer to = target.balance.getValue(txn);
            target.balance.setValue(to + amt, txn);

        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        List<CompletableFuture<Void>> collect =
                IntStream
                        .range(1, 10)
                        .mapToObj(i -> CompletableFuture.runAsync(() -> {
                            a.transfer(b, 100);
                        }))
                        .toList();
        for (CompletableFuture<Void> voidCompletableFuture : collect) {
            voidCompletableFuture.get();
        }
        System.out.println(a.balance.curRef.value());
        System.out.println(a.balance.curRef.version());
        System.out.println(b.balance.curRef.value());
        System.out.println(b.balance.curRef.version());

    }
}
