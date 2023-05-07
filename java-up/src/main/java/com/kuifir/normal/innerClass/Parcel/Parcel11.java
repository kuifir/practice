package com.kuifir.normal.innerClass.Parcel;

import com.kuifir.normal.innerClass.Contents;
import com.kuifir.normal.innerClass.Destination;

/**
 * 如果不需要内部类对象和外部类对象之间的连接，可以将内部类设置为static的。
 * 这种通常成为嵌套类。
 * 普通内部类对象中隐式地保留了一个引用，指向创建该对象的外部类对象。对于static的内部类来说，情况就不是这样了，嵌套类意味着：
 * 1. 不需要一个外部类来创建嵌套类对象；
 * 2. 无法从嵌套类对象内部访问非static的外部类对象。
 * <p/>
 * 从另一方面来看，嵌套类和普内部类还有些不同。
 * 1. 普通内部类的字段和方法，只能放在类的外部层次中，所以普通内部类中不能有static数据，static字段，也不能包含嵌套类。但是嵌套类可以包含所有这些内容。
 * 2. 普通内部类可以使用特殊的this引用来创建指向外部类对象的连接。而嵌套类，没有特殊的this引用。
 * @author kuifir
 * @date 2023/5/7 15:51
 */
public class Parcel11 {
    private static class ParcelContents implements Contents {
        private int i = 11;

        @Override
        public int value() {
            return i;
        }
    }

    protected static class ParcelDestination implements Destination {
        private String label;
        /**
         * 嵌套类可以包含其他静态元素(静态变量，静态方法，静态类)
         */
        static int x = 10;

        public static void f() {

        }

        static class AnotherLevel {
            static int x = 10;

            public static void f() {

            }
        }

        public ParcelDestination(String whereTo) {
            this.label = whereTo;
        }

        @Override
        public String readLabel() {
            return label;
        }
    }

    public static Destination destination(String s) {
        return new ParcelDestination(s);
    }

    public static Contents contents() {
        return new ParcelContents();
    }

    public static void main(String[] args) {
        Contents contents = contents();
        Destination tasmania = destination("Tasmania");
        // Parcel11$ParcelContents@7aec35a
        System.out.println(contents);
        // Parcel11$ParcelDestination@67424e82
        System.out.println(tasmania);
        ParcelDestination.AnotherLevel anotherLevel = new ParcelDestination.AnotherLevel();
        //Parcel11$ParcelDestination$AnotherLevel@531d72ca
        System.out.println(anotherLevel);
    }
}
