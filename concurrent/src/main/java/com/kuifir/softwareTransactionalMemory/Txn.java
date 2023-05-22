package com.kuifir.softwareTransactionalMemory;

/**
 * @author kuifir
 * @date 2023/5/22 21:58
 */
public interface Txn {
    <T> T get(TxnRef<T> ref);

    <T> void set(TxnRef<T> ref, T value);
}
