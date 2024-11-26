package com.kuifir.invoke.method.invokedynamic.methodHandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Random;

/**
 * method模拟invokevirtual指令
 */
public class MethodHandleTest {
    static class ClassA {
        public void println(String s) {
            System.out.println("ClassA "+s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = new Random().nextBoolean() ? new ClassA() : System.out;
        MethodHandle printlnMethodHAndle = getPrintlnMethodHAndle(obj);
        printlnMethodHAndle.invokeExact("aaa");
    }

    static MethodHandles.Lookup getLookup() {
        return MethodHandles.lookup();
    }

    private static MethodHandle getPrintlnMethodHAndle(Object obj) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return getLookup().findVirtual(obj.getClass(), "println", methodType)
                .bindTo(obj);
    }
}
