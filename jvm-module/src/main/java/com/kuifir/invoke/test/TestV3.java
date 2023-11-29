package com.kuifir.invoke.test;

import java.lang.reflect.Method;

/**
 * @author kuifir
 * @date 2023/11/29 21:24
 */
public class TestV3 {
    public static void target(int i) {
//        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("com.kuifir.invoke.test.TestV3");
        Method method = aClass.getMethod("target", int.class);
        // v3
        Object[] arg = new Object[1];
        // 在循环外构造参数数组
        arg[0] = 128;
        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            method.invoke(null, arg);
        }
    }
}
