package com.kuifir.switchExpression.problem;

/**
 * 加入 Shape 是基础类库，ProblemWithoutPatternMatching是第三方维护的扩展类库
 *
 * 然而，基础类库更新之后（添加新的类）扩展类库意识到扩展类库需要更改，并不是一件容易的事情
 *
 */
public class ProblemWithoutPatternMatching {

    /**
     * @since 1.0
     */
    public static boolean isSquare(Shape shape) {
        // @since 1.0 是对的，但是 对@since 2.0是错的
        return (shape instanceof Shape.Square);
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
