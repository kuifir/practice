package com.kuifir.softwareTransactionalMemory;

/**
 * @author kuifir
 * @date 2023/5/22 22:40
 */
@FunctionalInterface
public interface TxnRunnable {
    void run(Txn txn);
}
