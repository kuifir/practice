package com.kuifir.normal.innerClass;

/**
 * 如果必须执行某个类似构造器的动作：
 * 因为内部类没有名字，所以不可能有命名的构造器。借助`实例初始化`，我们可以在效果上为匿名内部类创建一个构造器
 *
 * @author kuifir
 * @date 2023/5/7 15:13
 */
public class AnonymousConstructor {
    /**]
     *
     * @param i 被传给了匿名内部类的基类构造器，但在该匿名内部类内部，它没有被直接使用到
     * @return
     */
    public static Base getBase(int i) {
        return new Base(i) {
            {
                System.out.println("Inside instance initializer");
            }

            @Override
            public void f() {
                System.out.println("In anonymous f()");
            }
        };
    }

    public static void main(String[] args) {
        Base base = getBase(47);
        base.f();
    }
}

abstract class Base {
    Base(int i) {
        System.out.println("Base constructor , i = " + i);
    }

    /**
     * 抽象方法
     */
    public abstract void f();
}
