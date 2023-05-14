package com.kuifir.normal.initialization.orderOfInitialization;

/**
 * 初始化的全过程:
 * 每个类的编译代码都存在于自己的单独文件中.
 * 只有在需要它的代码的时候才会加载该文件.
 * 一般可以认为"类的代码在第一次使用时才加载".
 * 这通常是在构造该类的第一个对象时,但在访问静态字段或静态方法时也会触发加载.
 * 尽管没有显示指定static关键字,但构造器也是一个静态方法.
 * 所以准确地说每当一个类的任何静态成员被访问时,都会触发它的加载.
 * <p></p>
 * 静态初始化也发生在初次使用之时.所有静态对象和静态代码块都在加载时按文本顺序(在类定义中编写它们的顺序)初始化.
 * 静态成员值初始化一次.
 * <p></p>
 * 当运行java Beetle时,首先会尝试访问静态方法Beetle.main(),所以加载器回去Beetle.class文件中找到Beetle类的编译代码.
 * 在加载它的代码时,加载器注意到有一个基类,然后它就回去加载基类.
 * 无论是否创建该类的对象, 都会发生这种情况(注释掉对象创建之后仍会加载基类).
 * <p></p>
 * 如果基类又有自己的基类,那么第二个基类也将会被加载,以此类推.
 * 接下来会执行根基类中的静态初始化,然后是下一个子类,以此类推.
 * 这很重要,因为子类的静态初始化可能依赖于基类成员正确初始化.
 * <p></p>
 * 现在所有必要的类都已加载,因此可以创建对象了.
 * 首先该对象中的所有基本类型都被设为默认值,并且对象引用被设为null-这通过将对象中的内存设置为二进制零来一步实现.
 * 然后调用基类构造器.这里的调用时是自动的,但也可以通过super关键字来指定基类构造器的调用(需要作为Beetle构造器中的第一个操作).
 * 基类构造器以 与子类构造器相同的顺序经历相同的过程.
 * 基类构造器完成后,子类的实例变量按文本顺序初始化.最后执行子类构造器的其余部分.
 * {@link OrderOfInitialization}
 *
 * @author kuifir
 * @date 2023/5/14 21:09
 */
public class Beetle extends Insect {
    private int k = printInit("Beetle.k initialized");
    public Beetle(){
        System.out.println("k = " + k);
        System.out.println("j = " + j);
    }
    private static int x2 = printInit("static Beetle.x2 initialized");

    public static void main(String[] args) {

        System.out.println("Beetle constructor");
        new Beetle();
        // 结果:
        // static Insect.x1 initialized
        // static Beetle.x2 initialized
        // Beetle constructor
        // i = 9, j = 0
        // Beetle.k initialized
        // k = 47
        // j = 39
    }
}

class Insect {
    private int i = 9;
    protected int j;

    Insect() {
        System.out.println("i = " + i + ", j = " + j);
        j = 39;
    }
    private static int x1 = printInit("static Insect.x1 initialized");
    static int printInit(String s){
        System.out.println(s);
        return 47;
    }
}