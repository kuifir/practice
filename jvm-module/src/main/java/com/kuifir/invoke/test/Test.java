package com.kuifir.invoke.test;

import java.lang.reflect.Method;

/**
 * @author kuifir
 * @date 2023/11/29 21:24
 */
public class Test {
    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("com.kuifir.invoke.test.Test");
        Method method = aClass.getMethod("target", int.class);
        method.invoke(null,0);
    }

}
