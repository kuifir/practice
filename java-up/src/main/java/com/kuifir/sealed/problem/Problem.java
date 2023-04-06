package com.kuifir.sealed.problem;

public class Problem {
    // 判断是不是正方形
    static boolean isSquare(Shape shape) {
        // 当只有正方形的时候这样判断,
//        return (shape instanceof Square);
        // 但是当有其他拓展类的时候,比如长款相等的长方形的时候,有直角的菱形,这样判断就不正确了
        if (shape instanceof Rectangle rect) {
            return (rect.length == rect.width);
        }
        return (shape instanceof Square);
        // 无限制的扩展性，是问题的根源
    }

    public abstract class Shape {
        public final String id;

        public Shape(String id) {
            this.id = id;
        }

        public abstract double area();
    }

    public class Square extends Shape {
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

    public class Rectangle extends Shape {
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
}
