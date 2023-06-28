package com.kuifir.basic.constructorSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kuifir
 * @date 2023/6/28 23:23
 */
public class GuardedIDField implements HasID{
    private static AtomicInteger counter = new AtomicInteger();
    private int id = counter.getAndIncrement();
    @Override
    public int getID() {
        return id;
    }

    public static void main(String[] args) {
        IDChecker.test(GuardedIDField::new);
    }
}
