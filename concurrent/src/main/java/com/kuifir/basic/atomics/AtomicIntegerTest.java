package com.kuifir.basic.atomics;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kuifir
 * @date 2023/7/1 15:55
 */
public class AtomicIntegerTest extends IntTestable {
    private AtomicInteger i = new AtomicInteger(0);

    @Override
    void evenIncrement() {
        i.addAndGet(2);
    }

    @Override
    public int getAsInt() {
        return i.get();
    }

    public static void main(String[] args) {
        Atomicity.test(new AtomicIntegerTest());
    }
}
