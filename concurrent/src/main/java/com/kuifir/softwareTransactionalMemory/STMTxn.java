package com.kuifir.softwareTransactionalMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * STMTxn 是 Txn 最关键的一个实现类，事务内对于数据的读写，都是通过它来完成的。
 * <p></p>
 * STMTxn 内部有两个 Map：
 * inTxnMap，用于保存当前事务中所有读写的数据的快照；
 * writeMap，用于保存当前事务需要写入的数据。
 * 每个事务都有一个唯一的事务 ID txnId，这个 txnId 是全局递增的。
 * <p></p>
 * STMTxn 有三个核心方法，分别是读数据的 get() 方法、写数据的 set() 方法和提交事务的 commit() 方法:
 * get() 方法将要读取数据作为快照放入 inTxnMap，同时保证每次读取的数据都是一个版本。
 * set() 方法会将要写入的数据放入 writeMap，但如果写入的数据没被读取过，也会将其放入 inTxnMap。
 * commit() 方法，我们为了简化实现，使用了互斥锁，所以事务的提交是串行的。
 * commit() 方法的实现很简单，首先检查 inTxnMap 中的数据是否发生过变化，
 * 如果没有发生变化，那么就将 writeMap 中的数据写入（这里的写入其实就是 TxnRef 内部持有的 curRef）；
 * 如果发生过变化，那么就不能将 writeMap 中的数据写入了。
 *
 * @author kuifir
 * @date 2023/5/22 22:09
 */
public final class STMTxn implements Txn {
    /**
     * 事务ID生成器
     */
    private static AtomicLong txnSeq = new AtomicLong(0);
    /**
     * 当前事务所有的相关数据
     */
    private Map<TxnRef, VersionedRef> inTxnMap = new HashMap<>();
    /**
     * 当前事务所有需要修改的数据
     */
    private Map<TxnRef, Object> writeMap = new HashMap<>();
    /**
     * 当前事务ID
     */
    private long txnId;

    /**
     * 构造函数，自动生成当前事务ID
     */
    STMTxn() {
        txnId = txnSeq.incrementAndGet();
    }

    /**
     * 获取当前事务中的数据
     * 方法将要读取数据作为快照放入 inTxnMap，同时保证每次读取的数据都是一个版本。
     *
     * @param ref
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(TxnRef<T> ref) {
        //将需要读取的数据，加入inTxnMap
        if (!inTxnMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.curRef);
        }
        return (T) inTxnMap.get(ref).value();
    }

    /**
     * 在当前事务中修改数据
     * set() 方法会将要写入的数据放入 writeMap，但如果写入的数据没被读取过，也会将其放入 inTxnMap。
     *
     * @param ref
     * @param value
     * @param <T>
     */
    @Override
    public <T> void set(TxnRef<T> ref, T value) {
        //将需要修改的数据，加入inTxnMap
        if (!inTxnMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.curRef);
        }
        writeMap.put(ref, value);
    }

    /**
     * 提交事务
     * commit() 方法，我们为了简化实现，使用了互斥锁，所以事务的提交是串行的。
     * commit() 方法的实现很简单，首先检查 inTxnMap 中的数据是否发生过变化，
     * 如果没有发生变化，那么就将 writeMap 中的数据写入（这里的写入其实就是 TxnRef 内部持有的 curRef）；
     * 如果发生过变化，那么就不能将 writeMap 中的数据写入了。
     */
    boolean commit() {
        synchronized (STM.commitLock) {
            // 是否校验通过
            boolean isValid = true;
            // 校验所有读过的数据是否发生过变化
            for (Map.Entry<TxnRef, VersionedRef> entry : inTxnMap.entrySet()) {
                VersionedRef curRef = entry.getKey().curRef;
                VersionedRef readRef = entry.getValue();
                //通过版本号来验证数据是否发生过变化
                if (curRef.version() != readRef.version()) {
                    isValid = false;
                    break;
                }
            }
            //如果校验通过，则所有更改生效
            if (isValid) {
                writeMap.forEach((k, v) -> {
                    k.curRef = new VersionedRef(v, txnId);
                });
            }
            return isValid;
        }

    }
}
