package com.kuifir.juc.semaphore;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 信号量实现限流器
 * 限流器的设计，这里的限流，指的是不允许多于 N 个线程同时进入临界区。
 * <p>
 * 对象池呢，指的是一次性创建出 N 个对象，之后所有的线程重复利用这 N 个对象，当然对象在被释放前，也是不允许其他线程使用的。
 */
public class ObjectPool<T, R> {

    private final List<T> pool;
    private final Supplier<T> supplier;
    private final Semaphore semaphore;

    // 构造函数
    ObjectPool(int size, Supplier<T> supplier) {
        this.pool = new Vector<T>(10);
        this.supplier = supplier;
        T t = supplier.get();
        IntStream.range(0, size).forEach(i -> pool.add(t));
        semaphore = new Semaphore(size);
    }

    // 利用对象池对象，调用fun
    R exec(Function<T, R> func) {
        T t = null;
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
//            pool.add(null);
            pool.add(supplier.get());
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ObjectPool<Long, String> pool = new ObjectPool<>(10, () -> new Random().nextLong());
        // 通过对象获取t,然后执行
        List<CompletableFuture<Void>> completableFutures = IntStream.range(0, 11)
                .mapToObj(i -> CompletableFuture.runAsync(() -> pool.exec(t -> {
                    System.out.println(t);
                    return t.toString();
                })))
                .collect(Collectors.toList());
        completableFutures.forEach(CompletableFuture::join);
    }
}
