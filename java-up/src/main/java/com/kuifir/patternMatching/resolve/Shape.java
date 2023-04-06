package com.kuifir.patternMatching.resolve;

/*
 * Copyright (c) 2021, Xuelei Fan. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * 如果在一个范围内，无法确认目标变量是否被匹配，或者目标变量不能被匹配，都不能使用匹配变量。
 * 如果我们从编译器的角度去理解，也就是说，在一个范围里，如果编译器能够确定匹配变量已经被赋值了，那么它就可以在这个范围内使用；
 * 如果编译器不能够确定匹配变量是否被赋值，或者确定没有被赋值，那么他就不能在这个范围内使用。
 */


public sealed interface Shape permits Shape.Circle, Shape.Rectangle, Shape.Square {
    Shape.Rectangle rect = null;    // field variable

    record Circle(double radius) implements Shape {
        // blank
    }

    record Square(double side) implements Shape {
        // blank
    }

    record Rectangle(double length, double width) implements Shape {
        // blank
    }

    static void main(String[] args) {
        Shape shape = new Shape.Rectangle(10, 10);
        System.out.println("It should be ture that " + shape +
                " is a square: " + isSquare(shape));

        System.out.println();

        shape = new Shape.Circle(10);
        System.out.println("It cannot be ture that " + shape +
                " is a square: " + (!isSquare(shape)));
    }

    static boolean isSquare(Shape shape) {
        if (shape instanceof Rectangle rect) {
            // Field rect is shadowed, local rect is in scope
            System.out.println(
                    "This should be the local rect: " +
                            rect.equals(shape));
            return (rect.length == rect.width);
        }

        // Field rect is in scope, local rect is not in scope here
        System.out.println(
                "This should be the field rect: " + (rect == null));
        return (shape instanceof Square);
    }

    public static boolean isSquareImplA(Shape shape) {
        if (shape instanceof Rectangle rectangle) {
            // rect is in scope
            return rectangle.length() == rectangle.width();
        }
        // rect is not in scope here
        return shape instanceof Square;
    }

    public static boolean isSquareImplB(Shape shape) {
        if (!(shape instanceof Rectangle rectangle)) {
            // rect is not in scope here
            return shape instanceof Square;
        }

        // rect is in scope
        return rectangle.length() == rectangle.width();
    }

    public static boolean isSquareImplC(Shape shape) {
        return shape instanceof Square ||  // rect is not in scope here
                (shape instanceof Rectangle rectangle &&
                        rectangle.length() == rectangle.width());   // rect is in scope here
    }


//    public static boolean isSquareImplD(Shape shape) {
//        return shape instanceof Square ||  // rect is not in scope here
//                (shape instanceof Rectangle rectangle ||
//                        rectangle.length() == rectangle.width());   // rect is not in scope here
//    }

    //只不过，位与运算符两侧的表达式都要参与计算。
    // 也就是说，不管位与运算符左侧的运算是否成立，位与运算符右侧的运算都要计算出来。
    // 换句话说，无论左侧的类型匹配不匹配，右侧的匹配变量都要使用。
    // 这就违反了匹配变量的作用域原则，编译器不能够确定匹配变量是否被赋值。
    // 所以，这一段代码，也不能通过编译器的审查。
//    public static boolean isSquareImplE(Shape shape) {
//        return shape instanceof Square |  // rect is not in scope here
//                (shape instanceof Rectangle rectangle &
//                        rectangle.length() == rectangle.width());   // rect is not in scope here
//    }
    public static boolean isSquareImplF(Shape shape) {
        if (shape instanceof Rectangle rect) {
            // Field rect is shadowed, local rect is in scope
            System.out.println("This should be the local rect: " + rect);
            return rect.length() == rect.width();
        }        // Field rect is in scope, local rect is not in scope here
        System.out.println("This should be the field rect: " + rect);
        return shape instanceof Shape.Square;
    }

    public static boolean isSquareImplG(Shape shape) {
        if (!(shape instanceof Rectangle rect)) {
            // Field rect is in scope, local rect is not in scope here
            System.out.println("This should be the field rect: " + rect);
            return shape instanceof Shape.Square;
        }
        // Field rect is shadowed, local rect is in scope
        System.out.println("This should be the local rect: " + rect);
        return rect.length() == rect.width();
    }

}
