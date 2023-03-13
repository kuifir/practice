package com.kuifir.sigleton;

/**
 * 懒汉模式simple
 * 但要运行在多线程下，就会出现实例化多个类对象的情况。
 * 当线程 A 进入到 if 判断条件后，开始实例化对象，此时 instance 依然为 null；
 * 又有线程 B 进入到 if 判断条件中，之后也会通过条件判断，进入到方法里面创建一个实例对象。
 */
public final class LazySingleton {
    private static LazySingleton instance = null;//不实例化

    private LazySingleton() {
    }//构造函数

    public static LazySingleton getInstance() {//通过该函数向整个系统提供实例
        if (null == instance) {//当instance为null时，则实例化对象，否则直接返回对象
            instance = new LazySingleton();//实例化对象
        }
        return instance;//返回已存在的对象
    }
}
