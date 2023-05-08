package com.kuifir.normal.innerClass;

/**
 * 局部内部类
 * 内部类也可以在代码块阿内创建，通常是在方法体内。
 * 局部内部类不能使用访问权限修饰符，因为它不是外围类的组成部分，但是它可以访问当前代码块中的常量，以及外围类内中的所有成员。
 * <p/>
 * 局部内部类和匿名内部类的行文和功能是一样的。
 * 局部内部类的名字在方法外是无法使用的。
 * 局部内部类允许我们定义具名的构造器以及重载版本，而匿名类只能使用实例初始化。
 * @author kuifir
 * @date 2023/5/8 21:08
 */
public class LocalInnerClass {
    private int count = 0;

    Counter getCounter(final String name) {
        /**
         * 一个局部内部类
         */
        class LocalCounter implements Counter {
            LocalCounter() {
                // 局部内部类可以有一个构造器
                System.out.println("LocalCounter()");
            }

            @Override
            public int next() {
                // 访问局部的final变量
                System.out.print(name);
                return count++;
            }
        }
        return new LocalCounter();
    }

    /**
     * 使用匿名内部类实现同样的功能
     */
    Counter getCounter2(final String name) {
        return new Counter() {
            // 匿名内部类不能有具名的构造器，只有一个实例初始化部分
            {
                System.out.println("Counter()");
            }

            @Override
            public int next() {
                System.out.print(name);
                return count++;
            }
        };
    }

    public static void main(String[] args) {
        LocalInnerClass localInnerClass = new LocalInnerClass();
        Counter c1 = localInnerClass.getCounter("Local inner"),
        c2 = localInnerClass.getCounter2("Anonymous inner");
        for (int i = 0; i < 5; i++) {
            System.out.println(c1.next());
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(c2.next());
        }

    }
}

interface Counter {
    int next();
}