package com.kuifir.sigleton;

import java.util.ArrayList;
import java.util.List;

//懒汉模式 内部类实现
public final class LazySingletonWithInnerClass {
    public List<String> list = null;// list属性

    private LazySingletonWithInnerClass() {//构造函数
        list = new ArrayList<String>();
    }

    // 内部类实现
    public static class InnerSingleton {
        private static LazySingletonWithInnerClass instance=new LazySingletonWithInnerClass();//自行创建实例
    }

    public static LazySingletonWithInnerClass getInstance() {
        return InnerSingleton.instance;// 返回内部类中的静态变量
    }
}
