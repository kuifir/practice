package com.kuifir.basic.atomics.josh;

/**
 * 第二个示例：一个生成序列号的类
 * Java的自增操作并不是原子性的，他分别涉及读和写，因此即使是如此简单的操作，也有可能发生线程问题的可能。
 * 这个示例中用到了volatile,只是为了试验它能否起到作用。然而真正的问题在于nextSerialNumber()方法没有通过同步的方式访问共享的可变值。
 * 为了测试SerialNumbers,我们会创建一个不会耗尽内存（导致溢出）的set,以防检测出问题的时间过长。
 * 此处的{@link CircularSet}复用了用来存储这些int的内存，最后覆盖了旧值（复制操作通常都很快，基本上也可以用java.util.Set来替代）
 * @author kuifir
 * @date 2023/7/1 15:26
 */
public class SerialNumbers {
    private volatile int serialNumber = 0;
    public int nextSerialNumber(){
        return serialNumber++;
    }
}
