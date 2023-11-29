package com.kuifir.invoke.test;

import java.lang.reflect.Method;

/**
 * @author kuifir
 * @date 2023/11/29 21:24
 */
public class TestV1 {
    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("com.kuifir.invoke.test.TestV1");
        Method method = aClass.getMethod("target", int.class);
        // v1
        for (int i = 0; i < 20; i++) {
            method.invoke(null, i);
        }
    }
}
