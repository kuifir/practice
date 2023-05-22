package com.kuifir.softwareTransactionalMemory;

/**
 * 所有对数据的读写操作，一定是在一个事务里面，
 * {@link TxnRef} 这个类负责完成事务内的读写操作，读写操作委托给了接口 {@link Txn}，
 * {@link Txn} 代表的是读写操作所在的当前事务， 内部持有的 {@link TxnRef#curRef} 代表的是系统中的最新值。
 *
 * @author kuifir
 * @date 2023/5/22 21:58
 */
public class TxnRef<T> {
    /**
     * 当前数据，带版本号
     */
    volatile VersionedRef curRef;

    public TxnRef(T curRef) {
        this.curRef = new VersionedRef(curRef, 0L);
    }

    /**
     * 获取当前事务中的数据
     */
    public T getValue(Txn txn) {
        return txn.get(this);
    }

    /**
     * 在当前事务中设置数据
     */
    public void setValue(T value, Txn txn) {
        txn.set(this, value);
    }
}
