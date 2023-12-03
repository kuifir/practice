package com.kuifir.invoke.method.invokedynamic.methodHandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @author kuifir
 * @date 2023/11/30 20:46
 */
public class Demo {
    // -XX:+UnlockDiagnosticVMOptions -XX:+ShowHiddenFrames这个参数来打印被 Java 虚拟机隐藏了的栈信息
    class Foo {
        private static void bar(Object o) {
            new Exception().printStackTrace();
        }

        public static MethodHandles.Lookup lookup() {
            return MethodHandles.lookup();
        }
    }

    public void test(MethodHandle mh, String s) throws Throwable {
        mh.invokeExact(s);
        mh.invokeExact((Object) s);
    }

    public static void main(String[] args) throws Throwable {
        // 获取方法句柄的不同方式
        MethodHandles.Lookup l = Foo.lookup();
        // 具备Foo类的访问权限
        Method m = Foo.class.getDeclaredMethod("bar", Object.class);
        MethodHandle mh0 = l.unreflect(m);
        MethodType t = MethodType.methodType(void.class, Object.class);
        MethodHandle mh1 = l.findStatic(Foo.class, "bar", t);
        mh1.invokeExact(new Object());
    }
}
