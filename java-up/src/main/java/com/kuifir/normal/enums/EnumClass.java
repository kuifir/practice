package com.kuifir.normal.enums;

/**
 * enum基本特性
 * 你可以调用枚举类型中的value()方法来遍历枚举常量列表。
 * value()方法生成一个有枚举常量组成的数组，其中常量的顺序和常量声明的顺序保持一致。
 * <p></p>
 * 当创建枚举类型时，编译器会为你生成一个辅助类，这个类自动继承自{@link java.lang.Enum}
 * {@link java.lang.Enum}提供了一些基本功能。
 * <p></p>
 * {@link java.lang.Enum#ordinal()}方法返回一个从0开始的int值，代表每个枚举实例的声明顺序。final修饰，不可重写。
 * 可以放心使用{@code ==}来比较枚举实例（equals()和hashCode()方法会由编译器自动生成）
 * {@link java.lang.Enum}类实现了Comparable接口(因此可比较)，所以自动包含了compareTo()方法，另外他还实现了Serializable接口（因此可序列化）。
 *
 * @author kuifir
 * @date 2023/5/30 22:10
 * @see java.lang.Enum
 * @see
 */
public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values()) {
            System.out.println(s + " ordinal: " + s.ordinal());
            System.out.println(s.compareTo(Shrubbery.CRAWLING) + " ");
            System.out.println(s.equals(Shrubbery.CRAWLING) + " ");
            System.out.println(s == Shrubbery.CRAWLING);
            System.out.println(s.getClass());
            System.out.println(s.getDeclaringClass());
            System.out.println(s.name());
            System.out.println("*********************");
        }
        for (String s : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrubbery = Enum.valueOf(Shrubbery.class, s);
            System.out.println(s);
        }
    }
}

/**
 * 灌木丛
 */
enum Shrubbery {
    /**
     * 土地
     */
    GROUND,
    /**
     * 昆虫
     */
    CRAWLING,
    /**
     * 藤蔓
     */
    HANGING;

}
