package com.kuifir.invoke.method.invokedynamic.methodHandle;

import java.lang.invoke.*;

/**
 * methodHandle模拟invokeDynamic
 */

public class InvokeDynamicTest {

    public static void main(String[] args) throws Throwable {
        INDYBootStrapMethod().invokeExact("aaa");
    }

    public static void testMethod(String s) {
        System.out.println("hello String：" + s);
    }

    public static CallSite BootStrapMethod(MethodHandles.Lookup caller,
                                           String methodName,
                                           MethodType methodType)
            throws NoSuchMethodException, IllegalAccessException {
        return new ConstantCallSite(caller.findStatic(InvokeDynamicTest.class, methodName, methodType));
    }

    private static MethodType MT_BootStrapMethod() {
        return MethodType
                .fromMethodDescriptorString(
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        null);
    }

    private static MethodHandle MH_BootStrapMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findStatic(InvokeDynamicTest.class,
                "BootStrapMethod",
                MT_BootStrapMethod());
    }

    private static MethodHandle INDYBootStrapMethod() throws Throwable {
        CallSite cs = (CallSite) MH_BootStrapMethod()
                .invokeWithArguments(MethodHandles.lookup(),
                        "testMethod",
                        MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null)
                );
        return cs.dynamicInvoker();
    }

}
