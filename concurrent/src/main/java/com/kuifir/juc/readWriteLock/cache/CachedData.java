package com.kuifir.juc.readWriteLock.cache;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁降级
 */
class CachedData {
    Object data;
    boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData() {
        rwl.readLock().lock();
        if (!cacheValid) {
            // Must release read lock before acquiring write lock
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {
                // Recheck state because another thread might have
                // acquired write lock and changed state before we did.
                if (!cacheValid) {
//                    data = ...;
                    cacheValid = true;
                }
                // 释放写锁前，降级为读锁
                // 降级是可以的
                // Downgrade by acquiring read lock before releasing write lock
                rwl.readLock().lock();
            } finally {
                rwl.writeLock().unlock();
                // Unlock write, still hold read
            }
        } try {
            // 此处仍然有读锁
//            use(data);
        } finally {
            rwl.readLock().unlock();
        }
    }
}
