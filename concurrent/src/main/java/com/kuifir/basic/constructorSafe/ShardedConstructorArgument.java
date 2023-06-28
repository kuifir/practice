package com.kuifir.basic.constructorSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 此处{@link SharedUser}的构造器共享了相同的参数。
 * 尽管SharedUser是通过完全无害且合理的方式来使用它的参数的，但<b>构造器的调用方式</b>导致了冲突。
 * SharedUser甚至无法知道它以这种方式被使用了，更不用说去控制它了！
 * 虽然语言层面并不支持synchronized修饰的构造器，但是可以通过synchronized语句块，来创建自己的（同步）构造器。
 * 虽然JLS声明"...这会阻塞正在创建的对象"，但这并不是真的——构造器事实上是个静态方法，
 * 因此synchronized的构造器实际上会阻塞Class对象。
 * 我们可以通过创建自己的静态对象并对它上锁来复现该过程：{@link SyncConstructor}
 *
 * @author kuifir
 * @date 2023/6/28 23:26
 */
public class ShardedConstructorArgument {
    public static void main(String[] args) {
        Unsafe unsafe = new Unsafe();
        IDChecker.test(() -> new SharedUser(unsafe));
        Safe safe = new Safe();
        IDChecker.test(() -> new SharedUser(safe));
    }
}

interface SharedArg {
    int get();
}

class Unsafe implements SharedArg {
    private int i = 0;

    @Override
    public int get() {
        return i++;
    }
}

class Safe implements SharedArg {
    private static AtomicInteger counter = new AtomicInteger();

    @Override
    public int get() {
        return counter.getAndIncrement();
    }
}

class SharedUser implements HasID {
    private final int id;

    SharedUser(SharedArg sa) {
        id = sa.get();
    }

    @Override
    public int getID() {
        return id;
    }
}
