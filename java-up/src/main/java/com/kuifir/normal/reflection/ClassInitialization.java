package com.kuifir.normal.reflection;

import java.util.Random;

/**
 * 类的字面量：
 * 使用方法{@code 类.class}
 * 这样更简单也更安全，因为它会进行编译时检查（因此不必放在try块中）。
 * 另外它还消除了对forName()方法的调用，所以效率也更高。
 * <p></p>
 * 类字面量适用于常规类以及接口、数组和基本类型。
 * 此外，每个基本包装类都有一个名为TYPE的标准字段。
 * TYPE字段表示一个指向和基本类型对应的Class对象的引用，
 * <pre>{@code
 *      boolean.class -> Boolean.TYPE
 *      char.class -> Character.TYPE
 *      byte.class -> Byte.TYPE
 *      short.class -> Short.TYPE
 *      int.class -> Integer.TYPE
 *      long.class -> Long.TYPE
 *      float.class -> Float.TYPE
 *      double.class -> Double.TYPE
 *      void.class -> Void.TYPE
 * }</pre>
 * <p></p>
 * 请注意，使用".class"的形式创建Class对象的引用时，该Class对象不会自动初始化。
 * Class.forName()会立即初始化类以产生Class引用，
 * <p></p>
 * 实际上，在使用一个类之前，需要执行以下3个步骤：
 * 1.加载。这是由类加载器执行的。该步骤会先找到字节码(通常在类路径的磁盘上，但也不一定)，然后从这些字节码中创建一个Class对象
 * 2.链接。链接阶段会因证类中的字节码，为静态变量分配存储空间，并在必要时解析该类对其它类的所有引用。
 * 3.初始化。如果有基类的话，会先初始化基类，执行静态初始化器和静态初始化块。
 * 初始化被延迟到首次引用静态方法(构造器是隐式静态的)或非常量静态字段时。
 *<p></p>
 * 使用".class"的形式创建Class对象的引用时，该Class对象不会自动初始化。
 * Class.forName()会立即初始化类以产生Class引用。
 *
 * 如果一个static final 字段的值是"编译时常量"，比如{@link Initable#STATIC_FINAL},那么这个值不需要初始化Initable类就能读取。
 * 但是把一个字段设置为static和final并不能保证这种行为：对{@link Initable#STATIC_FINAL2}的访问会强制之形类的初始化，因为它不是编译时常量
 * 如果static字段不是final的，那么访问它时，如果想要正常读取，总是需要先进行链接(为字段分配存储)和初始化(初始化该存储)，
 * 正如在对{@link Initable2#staticNonFinal}的访问中看到的那样。
 * @see ClassInitialization
 * @see Initable
 * @see Initable2
 * @see Initable3
 * @author kuifir
 * @date 2023/5/16 22:59
 */
public class ClassInitialization {
    public static Random rand = new Random(47);

    public static void main(String[] args) throws ClassNotFoundException {
        // 使用".class"的形式创建Class对象的引用时，该Class对象不会自动初始化。
        // 初始化被延迟到首次引用静态方法(构造器是隐式静态的)或非常量静态字段时。
        Class<Initable> initableClass = Initable.class;
        System.out.println("After creating Initable ref");
        // 不会触发初始化
        System.out.println(Initable.STATIC_FINAL);
        // 触发初始化
        System.out.println(Initable.STATIC_FINAL2);
        // 触发初始化
        System.out.println(Initable2.staticNonFinal);
        // Class.forName()会立即初始化类以产生Class引用，
        Class<?> initable3 = Class.forName("com.kuifir.normal.reflection.Initable3");
        System.out.println("After creating Initable3 ref");
        System.out.println(Initable3.staticNonFinal);

    }

}

class Initable {
    static final int STATIC_FINAL = 47;
    static final int STATIC_FINAL2 = ClassInitialization.rand.nextInt(1000);
    static {
        System.out.println("Initializing Initable");
    }
}
class Initable2{
    static int staticNonFinal = 147;
    static {
        System.out.println("Initializing Initable2");
    }
}
class Initable3{
    static int staticNonFinal = 74;
    static {
        System.out.println("Initializing Initable3");
    }
}