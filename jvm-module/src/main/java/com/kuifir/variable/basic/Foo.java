package com.kuifir.variable.basic;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author kuifir
 * @date 2023/11/4 16:07
 */
public class Foo {
     boolean boolValue;
    public boolean getFlag() {
        return this.boolValue;
    }
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // 将这个true替换为2或者3，再看看打印结果
        Foo foo = new Foo();
        foo.boolValue = true;
        // 查看Unsafe是否执行掩码操作
        Field field = foo.getClass().getDeclaredField("boolValue");
        Unsafe unsafe = getUnsafeByConstructor();
        long addr = unsafe.objectFieldOffset(field);

        for (byte i = 0; i < 4; i++) {
            System.out.println("Unsafe.putByte: " + i);
            unsafe.putByte(foo,addr,i);
            // 总是会打印出put的值
            System.out.println("Unsafe.getByte: " + unsafe.getByte(foo, addr));
            // 打印出的值，像是ifeq, flag != 0即true
            System.out.println("Unsafe.getBoolean: " + unsafe.getBoolean(foo, addr));

            // 像是ifeq, flag != 0即true
            System.out.println("foo.flag: " + foo.boolValue);
            // 像是 ((flag) & 1)
            System.out.println("foo.getFlag: " +foo.getFlag());

            if (foo.boolValue) {
                System.out.println("Hello, Java!" + foo.boolValue);
            }
            if (foo.boolValue == true) {
                System.out.println("Hello, JVM!" + foo.boolValue);
            }
        }
    }

    private static Unsafe getUnsafeByConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
        unsafeConstructor.setAccessible(true);
        Unsafe unsafe = unsafeConstructor.newInstance();

        return unsafe;
    }

    private static Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        return unsafe;
    }
}