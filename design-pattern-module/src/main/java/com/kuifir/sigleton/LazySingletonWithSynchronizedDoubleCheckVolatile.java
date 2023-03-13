package com.kuifir.sigleton;

//懒汉模式 + synchronized同步锁

import java.util.ArrayList;
import java.util.List;

/**
 * 当多个线程进入到 if 判断条件里，虽然有同步锁，但是进入到判断条件里面的线程依然会依次获取到锁创建对象，然后再释放同步锁。
 * 所以我们还需要在同步锁里面再加一个判断条件：。
 *
 * 在执行 instance = new Singleton(); 代码时，正常情况下，实例过程这样的：
 *
 * 给 Singleton 分配内存；
 * 调用 Singleton 的构造函数来初始化成员变量；
 * 将 Singleton 对象指向分配的内存空间（执行完这步 singleton 就为非 null 了）。
 * 如果虚拟机发生了重排序优化，这个时候步骤 3 可能发生在步骤 2 之前。
 * 如果初始化线程刚好完成步骤 3，而步骤 2 没有进行时，则刚好有另一个线程到了第一次判断, 这个时候判断为非 null，并返回对象使用，这个时候实际没有完成其它属性的构造，因此使用这个属性就很可能会导致异常。
 * 在这里，Synchronized 只能保证可见性、原子性，无法保证执行的顺序。
 *
 * 这个时候，就体现出 Happens-Before 规则的重要性了。 通过字面意思，你可能会误以为是前一个操作发生在后一个操作之前。
 * 然而真正的意思是，前一个操作的结果可以被后续的操作获取。 这条规则规范了编译器对程序的重排序优化。 我们知道 volatile 关键字可以保证线程间变量的可见性，简单地说就是当线程 A 对变量 X 进行修改后，在线程 A 后面执行的其它线程就能看到变量 X 的变动。 除此之外，volatile 在 JDK1.5 之后还有一个作用就是阻止局部重排序的发生，也就是说，volatile 变量的操作指令都不会被重排序。 所以使用 volatile 修饰 instance 之后，Double-Check 懒汉单例模式就万无一失了。
 *
 */
//懒汉模式 + synchronized同步锁 + double-check
//懒汉模式 + synchronized同步锁 + double-check
public final class LazySingletonWithSynchronizedDoubleCheckVolatile {
    private volatile static LazySingletonWithSynchronizedDoubleCheckVolatile instance= null;//不实例化
    public List<String> list = null;//list属性
    private LazySingletonWithSynchronizedDoubleCheckVolatile(){
        list = new ArrayList<String>();
    }//构造函数
    public static LazySingletonWithSynchronizedDoubleCheckVolatile getInstance(){//加同步锁，通过该函数向整个系统提供实例
        if(null == instance){//第一次判断，当instance为null时，则实例化对象，否则直接返回对象
            synchronized (LazySingletonWithSynchronizedDoubleCheckVolatile.class){//同步锁
                if(null == instance){//第二次判断
                    instance = new LazySingletonWithSynchronizedDoubleCheckVolatile();//实例化对象
                }
            }
        }
        return instance;//返回已存在的对象
    }
}
