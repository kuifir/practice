package com.kuifir.sigleton;

public class LazySingletonWithEnum {
    private static LazySingletonWithEnum instance = null;

    // 私有构造函数
    private LazySingletonWithEnum(){
    }

    public static LazySingletonWithEnum getInstance(){
        return Singleton.SINGLETON.getInstance();
    }

    private enum Singleton {
        SINGLETON;

        private LazySingletonWithEnum singleton;

        // JVM保证这个方法只调用一次
        Singleton(){
            singleton = new LazySingletonWithEnum();
        }

        public LazySingletonWithEnum getInstance(){
            return singleton;
        }
    }
}