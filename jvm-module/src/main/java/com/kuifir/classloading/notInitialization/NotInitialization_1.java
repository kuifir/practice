package com.kuifir.classloading.notInitialization;

/**
 * 被动使用类字段演示：
 * 通过子类引用父类的静态字段，不会导致子类初始化
 * 至于是否会触发子类的加载和验证阶段，在《Java 虚拟机规范》中并未明确指出，所以取决于虚拟机的具体实现
 * 对于HotSpot 虚拟机来说，可以通过 -XX:+TraceClassLoading(21使用 -Xlog:class+load=info,class*=info)参数观察到此操作是会导致子类加载的。
 */
public class NotInitialization_1 {

    public static void main(String[] args) {
        System.out.println(SubClass.value);
        System.out.println(SubClass.STATIC_FINAL_CONSTANTPOOL);
    }
}
