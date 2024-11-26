package com.kuifir.invoke.method.invokedynamic.methodHandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * MethodHandle 执行分派
 * 实现调用祖类方法
 * VM args: --add-opens=java.base/java.lang.invoke=ALL-UNNAMED
 */

public class MethodHandleDispatch {
    class GrandFather {
        void thinking() throws Throwable {
            System.out.println("I am grandfather");
        }
    }

    class Father extends GrandFather {
        @Override
        void thinking() throws Throwable {
            System.out.println("I am father");
        }
    }

    class Son extends Father {
        @Override
        void thinking() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(void.class);
            MethodHandle thinking =  lookup.findSpecial(GrandFather.class,
                            "thinking",
                            methodType,
                            getClass());
            thinking.invoke(this);
            // Java7 UPDATE10之后
            Field implLookup = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            implLookup.setAccessible(true);
            MethodType mt = MethodType.methodType(void.class);
            MethodHandle methodHandle = ((MethodHandles.Lookup) implLookup.get(null))
                    .findSpecial(GrandFather.class,
                    "thinking",
                    mt,
                    GrandFather.class);
            methodHandle.invoke(this);
        }
    }

    public static void main(String[] args) throws Throwable {
        Son son = new MethodHandleDispatch().new Son();
        son.thinking();
    }
}
