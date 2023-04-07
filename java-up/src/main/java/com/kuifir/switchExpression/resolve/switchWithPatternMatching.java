package com.kuifir.switchExpression.resolve;

/**
 * 具有模式匹配能力的 switch，提升了 switch 的数据类型匹配能力。
 * switch 要匹配的数据，现在可以是整形的原始类型（数字、枚举、字符串），或者引用类型。
 * <p>
 * 支持 null 情景模式
 * 具有模式匹配能力的 switch，支持空引用的匹配。如果我们能够有意识地使用这个特性，可以提高我们的编码效率，降低代码错误。
 * </p>
 * 可类型匹配的情景
 * <p>
 * 穷举的匹配情景
 * 如果没有穷举，编译会报错，扩展类库的维护者就能够第一时间知道这个方法的缺陷。
 * <p>
 * 一般来说，只有我们能够确信，待匹配类型的升级，不会影响 switch 表达式的逻辑的时候，我们才能考虑使用缺省选择情景。
 */
public class switchWithPatternMatching {
    public static void main(String[] args) {

    }

    /**
     * @since 1.0
     */
    public static boolean isSquare(Shape shape) {
        // @since 1.0 是对的，但是 对@since 2.0是错的
//        return (shape instanceof Shape.Square);
        return switch (shape) {
            case null, Shape.Circle c -> false;
            case Shape.Square s -> true;
            case Shape.Rectangle rectangle -> rectangle.length == rectangle.width;
        };
    }

    public sealed interface Shape
            permits Shape.Circle, Shape.Rectangle, Shape.Square {


        /**
         * @since 1.0
         */
        record Circle(double radius) implements Shape {
            // blank
        }

        /**
         * @since 1.0
         */
        record Square(double side) implements Shape {
            // blank
        }

        /**
         * @since 2.0
         */
        record Rectangle(double length, double width) implements Shape {
            // blank
        }
    }
}
