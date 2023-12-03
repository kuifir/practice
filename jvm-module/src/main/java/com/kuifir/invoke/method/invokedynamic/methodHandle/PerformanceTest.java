package com.kuifir.invoke.method.invokedynamic.methodHandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author kuifir
 * @date 2023/11/30 21:09
 */
public class PerformanceTest {
    public void bar(Object o) {

    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup l = MethodHandles.lookup();
        MethodType t = MethodType.methodType(void.class, Object.class);
        MethodHandle mh = l.findVirtual(PerformanceTest.class, "bar", t);
        long current = System.currentTimeMillis();
        PerformanceTest performanceTest = new PerformanceTest();
        Object o = new Object();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            mh.invokeExact(performanceTest, o);
        }
    }
}
