package com.kuifir.sealed.resolve;

/**
 * <p>
 * 封闭类的声明使用 sealed 类修饰符，然后在所有的 extends 和 implements 语句之后，使用 permits 指定允许扩展该封闭类的子类。
 * <p>
 * 由 permits 关键字指定的许可子类（permitted subclasses），必须和封闭类处于同一模块（module）或者包空间（package）里。
 * 如果封闭类和许可类是在同一个模块里，那么它们可以处于不同的包空间里
 * <p>
 * 如果允许扩展的子类和封闭类在同一个源代码文件里，封闭类可以不使用 permits 语句，Java 编译器将检索源文件，在编译期为封闭类添加上许可的子类。
 *
 * <p>
 * 许可类的声明需要满足下面的三个条件：
 * 1. 许可类必须和封闭类处于同一模块（module）或者包空间（package）里，也就是说，在编译的时候，封闭类必须可以访问它的许可类；
 * 2.许可类必须是封闭类的直接扩展类；
 * 3. 许可类必须声明是否继续保持封闭：
 *    许可类可以声明为终极类（final），从而关闭扩展性；
 *    许可类可以声明为封闭类（sealed），从而延续受限制的扩展性；
 *    许可类可以声明为解封类（non-sealed）, 从而支持不受限制的扩展性。
 * <p>
 * 由于许可类必须是封闭类的直接扩展，因此许可类不具备传递性。
 * <p>
 */
public class Resolve {
    public static void main(String[] args) {
        Circle circle = new Circle("1", 2.0);
        Square square = new Square("2", 2.0);
        Rectangle rectangle = new Rectangle("3", 1.0, 2.0);
        Rectangle square2 = new Rectangle("3", 2.0, 2.0);
        System.out.println("circle is square: " + Shape.isSquare(circle));
        System.out.println("square is square: " + Shape.isSquare(square));
        System.out.println("rectangle is square: " + Shape.isSquare(rectangle));
        System.out.println("square2 is square: " + Shape.isSquare(square2));
    }


    //    封闭类的声明使用 sealed 类修饰符，然后在所有的 extends 和 implements 语句之后，使用 permits 指定允许扩展该封闭类的子类。
    // 如果允许扩展的子类和封闭类在同一个源代码文件里，封闭类可以不使用 permits 语句，Java 编译器将检索源文件，在编译期为封闭类添加上许可的子类。
//    public abstract sealed class Shape {
    public static abstract sealed class Shape permits Circle, Rectangle, Square {
        public final String id;

        public Shape(String id) {
            this.id = id;
        }

        public abstract double area();
        static boolean isSquare(Shape shape) {
            // 扩展已经限制住
            if (shape instanceof Rectangle rect) {
                return (rect.length == rect.width);
            }
            return (shape instanceof Square);
        }
    }

    public static sealed class Square extends Shape permits ColoredSquare {
        public final double side;

        public Square(String id, double side) {
            super(id);
            this.side = side;
        }

        @Override
        public double area() {
            return side * side;
        }
    }

    public static non-sealed class Rectangle extends Shape {
        public final double length;
        public final double width;

        public Rectangle(String id, double length, double width) {
            super(id);
            this.length = length;
            this.width = width;
        }

        @Override
        public double area() {
            return length * width;
        }
    }
//    由于许可类必须是封闭类的直接扩展，因此许可类不具备传递性。
//    也就是说，ColoredSquare 是 Square 的许可类，但不是 Shape 的许可类。
    public static final class ColoredSquare extends Square {
        public ColoredSquare(String id, double side) {
            super(id, side);
        } // snipped
    }

    public static non-sealed class Circle extends Shape {
        public final double radius;

        public Circle(String id, double radius) {
            super(id);
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }
}
