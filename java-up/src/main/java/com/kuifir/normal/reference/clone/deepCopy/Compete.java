package com.kuifir.normal.reference.clone.deepCopy;

import cn.hutool.core.date.StopWatch;

import java.io.*;

/**
 * 如果仔细想想Java的对象序列化，你可能就会发现，
 * 如果对一个对象先进性序列化，再将其反序列化，那么它实际上就是被克隆了。
 * 那为什么不用序列化来实现深拷贝呢？下面这个实例对这两种方式分别计时，比较两者有什么不同。
 *<p></p>
 * Thing2 和Ting4包含了成员对象，所以需要进行一些深拷贝操作。
 * Serializable类很容易构建，但是需要大量额外操作来复制它们。
 * 另外在克隆所需要的操作中，类的构建工作更多，但是实际的对象复制操作相对简单。
 * 注意，序列化至少要比克隆，慢一个数量级。
 * @author kuifir
 * @date 2023/6/6 22:18
 */
public class Compete {
    private static final int SIZE = 10_000;

    public static void main(String[] args) throws Exception {
        Thing2[] a = new Thing2[SIZE];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Thing2();
        }
        Thing4[] b = new Thing4[SIZE];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Thing4();
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try (
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                ObjectOutput oos = new ObjectOutputStream(buf);
        ) {
            for (Thing2 a1 : a) {
                oos.writeObject(a1);
            }
            // 现在获取副本
            try (ObjectInputStream in =
                         new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()))) {
                Thing2[] c = new Thing2[SIZE];
                for (int i = 0; i < SIZE; i++) {
                    c[i] = (Thing2) in.readObject();
                }
            }
        }
        stopWatch.stop();
        System.out.println("Duplication via serialization: " + stopWatch.getLastTaskTimeMillis() + " Milliseconds");
        // 现在试试克隆
        stopWatch.start();
        Thing4[] d = new Thing4[SIZE];
        for (int i = 0; i < SIZE; i++) {
            d[i] = b[i].clone();
        }
        stopWatch.stop();
        System.out.println("Duplication via serialization: " + stopWatch.getLastTaskTimeMillis() + " Milliseconds");
    }
}

class Thing1 implements Serializable {
}

class Thing2 implements Serializable {
    Thing1 t1 = new Thing1();
}

class Thing3 implements Cloneable {
    @Override
    protected Thing3 clone() {
        try {
            return (Thing3) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Thing4 implements Cloneable {
    private Thing3 t3 = new Thing3();

    @Override
    protected Thing4 clone() {
        Thing4 t4 = null;
        try {
            t4 = (Thing4) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        t4.t3 = t3.clone();
        return t4;
    }
}