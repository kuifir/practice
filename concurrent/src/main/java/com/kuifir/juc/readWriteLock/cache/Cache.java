package com.kuifir.juc.readWriteLock.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class Cache<K, V> {
    private final Map<K, V> map = new HashMap<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Supplier<V> supplier;

    public Cache(Supplier<V> supplier) {
        this.supplier = supplier;
    }

    // 读缓存
    V get(K key) {
        V value = null;
        // 读缓存
        readLock.lock();
        try {
            value = map.get(key);
            // 锁升级 （ReadWriteLock不支持,会无限等待（读锁还未释放，写锁一直等待））
            if (value == null) {
                writeLock.lock();
                try {
                    //再次验证并更新缓存
                    value = map.get(key);
                    if (value == null) {
                        //查询数据库
                        value = supplier.get();
                        map.put(key, value);
                    }
                } finally {
                    writeLock.unlock();
                }
            }
        } finally {
            readLock.unlock();
        }
        // 缓存中存在，返回
        if (value != null) {
            return value;
        }
        // 缓存中不存在，获取数据，放入缓存
        writeLock.lock();
        try {
            // 再次验证
            // 其他线程可能已经查询过数据库
            value = map.get(key);
            if (value == null) {
                //查询数据库
                // value=省略代码无数
                value = supplier.get();
                map.put(key, value);
            }
        } finally {
            writeLock.unlock();
        }
        return value;
    }

    // 写缓存
    V put(K key, V value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        Cache<Integer, Integer> cache = new Cache<>(new Random()::nextInt);
        System.out.println(cache.get(1));
    }
}
