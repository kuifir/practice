package com.kuifir.sigleton;

//懒汉模式 + synchronized同步锁

/**
 * 同步锁会增加锁竞争，带来系统性能开销，从而导致系统性能下降，因此这种方式也会降低单例模式的性能。
 * 还有，每次请求获取类对象时，都会通过 getInstance() 方法获取，除了第一次为 null，其它每次请求基本都是不为 null 的。
 * 在没有加同步锁之前，是因为 if 判断条件为 null 时，才导致创建了多个实例。
 * 基于以上两点，我们可以考虑将同步锁放在 if 条件里面，这样就可以减少同步锁资源竞争。
 *
 */
public final class LazySingletonWithSynchronizedMethod {
    private static LazySingletonWithSynchronizedMethod instance= null;//不实例化
    private LazySingletonWithSynchronizedMethod(){}//构造函数
    public static synchronized LazySingletonWithSynchronizedMethod getInstance(){//加同步锁，通过该函数向整个系统提供实例
        if(null == instance){//当instance为null时，则实例化对象，否则直接返回对象
            instance = new LazySingletonWithSynchronizedMethod();//实例化对象
        }
        return instance;//返回已存在的对象
    }
}
