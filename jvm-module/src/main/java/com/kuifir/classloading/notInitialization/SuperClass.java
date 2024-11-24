package com.kuifir.classloading.notInitialization;

public class SuperClass {
    static {
        System.out.println("SuperClass init");
    }
    public static int value =123;
    // 如果是被final修饰、已在编译器把结果放入常量池的静态字段，读取不会初始化该类
    public static final String STATIC_FINAL_CONSTANTPOOL ="hello world";
}
