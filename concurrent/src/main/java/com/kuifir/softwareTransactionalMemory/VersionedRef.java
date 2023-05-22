package com.kuifir.softwareTransactionalMemory;

/**
 * 带版本号的对象引用:
 * 这个类的作用就是将对象 value 包装成带版本号的对象。
 * 按照 MVCC 理论，数据的每一次修改都对应着一个唯一的版本号，所以不存在仅仅改变 value 或者 version 的情况，
 * 用不变性模式就可以很好地解决这个问题，
 * 所以 VersionedRef 这个类被我们设计成了不可变的。
 * <p></p>
 *
 * @author kuifir
 * @date 2023/5/22 21:55
 * @see Txn
 * @see TxnRef
 * @see VersionedRef
 */
public record VersionedRef<T>(T value, long version) {
}
