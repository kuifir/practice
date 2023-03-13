package com.kuifir.sigleton;

/**
 * 饿汉模式
 */
public final class HungerSingleton {
    private static HungerSingleton instance = new HungerSingleton();//自行创建实例

    private HungerSingleton() {
    }//构造函数

    public static HungerSingleton getInstance() {//通过该函数向整个系统提供实例
        return instance;
    }
}