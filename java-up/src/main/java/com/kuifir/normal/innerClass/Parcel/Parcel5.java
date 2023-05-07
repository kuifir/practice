package com.kuifir.normal.innerClass.Parcel;

import com.kuifir.normal.innerClass.Destination;

/**
 * 内部类可以在一个方法内或者任何一个作用域内创建。这么做有两个理由：
 * 1. 正如先前岩石的，你要实现某种接口，以便创建和返回一个引用
 * 2. 你再解决一个复杂的问题，在自己的解决方案中创建了一个类来辅助，但是你不想让它公开
 * <p/>
 * 后面几个示例，可以通过修改前面的代码来使用:
 * 1. 在方法中定义的类 {@link Parcel5}
 * 2. 在方法中的某个作用域内定义的类 {@link Parcel6}
 * 3. 实现某个接口的匿名类{@link Parcel7}
 * 4. 这样的匿名类-它继承了拥有非默认构造器的类{@link Parcel8}
 * 5. 执行字段初始化的匿名类{@link Parcel9}
 * 6. 它通过实例初始化来执行构造（匿名内部类不可能有构造器）的匿名类{@link Parcel10}
 * <p/>
 * 本例演示如何在一个方法的作用域内（而不是另一个类的作用域内）创建一个完整的类。这叫做局部内部类
 *
 * @author kuifir
 * @date 2023/5/6 22:58
 * @see Parcel5
 * @see Parcel6
 */
public class Parcel5 {
    public Destination destination(String s) {
        /**
         * 这个类时方法destination()的一部分，而不是Parcel5的一部分。因此DestinationImpl在Destination外不能访问。
         * 在同一子目录下的每个类内，都可以使用类标识符DestinationImpl来命名内部类，而不会产生名字冲突
         */
        final class DestinationImpl implements Destination {
            private final String label;

            private DestinationImpl(String whereTo) {
                label = whereTo;
            }

            @Override
            public String readLabel() {
                return label;
            }
        }
        // return 语句中的向上转型意味着destination()中只传出了一个指向Destination接口的引用。
        return new DestinationImpl(s);
    }

    public static void main(String[] args) {
        Parcel5 parcel5 = new Parcel5();
        Destination tasmania = parcel5.destination("Tasmania");
        // Parcel5$1DestinationImpl@277050dc
        System.out.println(tasmania);
        System.out.println(tasmania.readLabel());
    }
}
