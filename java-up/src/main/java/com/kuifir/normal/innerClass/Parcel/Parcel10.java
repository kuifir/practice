package com.kuifir.normal.innerClass.Parcel;

import com.kuifir.normal.innerClass.Destination;

/**
 * 在效果上，实例化部分就是匿名内部类的构造器。不过它也有局限性-我们无法重载实例初始化部分，所以只能有一个这样的构造器
 * <p/>
 * 与普通的类相比，匿名内部类有些局限性，因为他们要么是拓展一个类，要么是实现一个接口，但是两者不可兼得。
 * 而且就算要实现接口，也只能实现一个。
 * @author kuifir
 * @date 2023/5/7 15:23
 */
public class Parcel10 {
    /**
     * @param dest 必须是最终变量，或者是实际的最终变量，因为在匿名内部类中被用到
     * @param price 必须是最终变量，或者是实际的最终变量，因为在匿名内部类中被用到
     */
    public Destination destination(final String dest, final float price) {
        return new Destination() {
            private int cost;
            private String label = dest;

            // 为每个对象执行实例初始化
            {
                cost = Math.round(price);
                if (cost > 100) {
                    System.out.println("Over budget");
                }
            }

            @Override
            public String readLabel() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        Parcel10 parcel10 = new Parcel10();
        parcel10.destination("Tasmania", 101.395F);
    }
}
