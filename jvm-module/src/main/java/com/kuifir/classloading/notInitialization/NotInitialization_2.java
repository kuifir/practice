package com.kuifir.classloading.notInitialization;

/**
 * 被动使用类字段演示:
 * 通过数组定义来引用类，不会触发此类的初始化
 * VM args: -Xlog:class+load=info,class*=info
 */
public class NotInitialization_2 {

    public static void main(String[] args) {
       SuperClass[] sca = new SuperClass[10];
        System.out.println(sca.getClass());
    }
}
