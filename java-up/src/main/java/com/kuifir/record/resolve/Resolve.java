package com.kuifir.record.resolve;

/**
 * Java 档案类是用来表示不可变数据的透明载体。
 *
 * <p> 不可变
 * 1. Java 档案类不支持扩展子句，用户不能定制它的父类。隐含的，它的父类是 java.lang.Record。父类不能定制，也就意味着我们不能通过修改父类来影响 Java 档案的行为。
 * 2. Java 档案类是个终极（final）类，不支持子类，也不能是抽象类。没有子类，也就意味着我们不能通过修改子类来改变 Java 档案的行为。
 * 3. Java 档案类声明的变量是不可变的变量。这就是我们前面反复强调的，一旦实例化就不能再修改的关键所在。
 * 4. Java 档案类不能声明可变的变量，也不能支持实例初始化的方法。这就保证了，我们只能使用档案类形式的构造方法，避免额外的初始化对可变性的影响。
 * 5. Java 档案类不能声明本地（native）方法。如果允许了本地方法，也就意味着打开了修改不可变变量的后门。
 * <p> 透明载体
 * 透明载体的意思，通俗地说，就是档案类承载有缺省实现的方法，这些方法可以直接使用，也可以替换掉。
 * 默认实现的方法 : 构造方法equals, 方法hashCode, 方法toString', 方法不可变数据的读取方法
 * <p>
 * 如果声明的不可变变量没有重写 equals 方法和 hashCode 方法，
 * 那么这个档案类的 equals 方法和 hashCode 方法的行为就可能不是可以预测的。
 * 比如，如果不可变的变量是一个数组，通过下面的例子，我们来看看它的 equals 方法能不能正常工作。
 *
 * @<code> // jshell 执行
 * record Password(byte[] password) {}；
 * Password pA = new Password("123456".getBytes());
 * Password pB = new Password("123456".getBytes());
 * pA.equals(pB); // false
 * </code>
 * 如果把变量的类型换成重写了 equals 方法的字符串 String，我们就能看到预期的结果了。
 * @<code> jshell 执行
 * record Password(String password) {};
 * Password pA = new Password("123456");
 * Password pB = new Password("123456");
 * pA.equals(pB); // true
 * </code>
 * <p>
 * 不可变的圆形 Circle 类和正方形 Square 类相同点
 * <p>
 * 1. 就是使用公开的只读变量（使用 final 修饰符来声明只读变量）。
 * Circle 类的变量 radius，和 Square 类的变量 side，都是公开的只读的变量。
 * 这样的声明，是为了公开变量的只读性
 * 2. 公开的只读变量，需要在构造方法中赋值，而且只在构造方法中赋值，且这样的构造方法还是公开的方法。
 * Circle 类的构造方法给 radius 变量赋值，Square 类的构造方法给 side 变量赋值。
 * 这样的构造方法，解决了对象的初始化问题。
 * 3. 没有了读取的方法；公开的只读变量，替换了掉了公开的读取方法。这样的变化，使得代码量总体变少了
 * <p>
 * 这么多相似的地方，相似的代码，能不能进一步地简化呢
 */
public class Resolve {

    public static void main(String[] args) {
        // 实例化
        Circle circle = new Circle(10.0);
        // 获取变量
        double radius = circle.radius();
        System.out.println(radius);
//        档案类内置了缺省的 equals 方法、hashCode 方法以及 toString 方法的实现
        System.out.println(circle);
        Circle circle1 = new Circle(10.0);
        // 会是相同吗
        System.out.println(circle.equals(circle1)); // true
        System.out.println(circle.hashCode());
        System.out.println(circle1.hashCode());
        System.out.println(circle == circle1); // false
    }

    public interface Shape {
        double getArea();
    }

    /**
     *
     */
    public record Circle(double radius) implements Shape {

        // 替换缺省构造方法
        // 构造方法的声明没有参数，也没有给实例变量赋值的语句.这并不是说，构造方法就没有参数，或者实例变量不需要赋值。
        // 实际上，为了简化代码，Java 编译的时候，已经替我们把这些东西加上去了。
        // 所以，不论哪一种编码形式，构造方法的调用都是没有区别的。
        public Circle {
            if (radius < 0) {
                throw new IllegalArgumentException("The radius of a circle cannot be negative [" + radius + "]");
            }
        }

        @Override
        public double getArea() {
            return Math.PI * radius * radius;
        }

    }

    public record Square(double side) implements Shape {

        @Override
        public double getArea() {
            return side * side;
        }
    }
}
