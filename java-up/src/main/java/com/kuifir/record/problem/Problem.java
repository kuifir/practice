package com.kuifir.record.problem;

/**
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
public class Problem {

    public interface Shape {
        double getArea();
    }

    public final class CircleWithSynchronized implements Shape {
        private double radius;

        public CircleWithSynchronized(double radius) {
            this.radius = radius;
        }

        @Override
        public synchronized double getArea() {
            return Math.PI * radius * radius;
        }

        public synchronized double getRadius() {
            return radius;
        }

        public synchronized void setRadius(double radius) {
            this.radius = radius;
        }
    }

    /**
     * 这个对象是一个只读的对象，不支持修改->不可变对象
     * <p>
     * 1.天生线程安全: 一旦实例化就不能再修改,即使在多线程也不需要使用同步方法
     * 2.简化代码 : 删除了读取半径的方法,也就不需要将对外方法进行线程安全。取而代之的，是公开的半径这个变量。
     */
    public static final class Circle implements Shape {
        public final double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double getArea() {
            return Math.PI * radius * radius;
        }

    }

    public static class Square implements Shape {
        public final double side;

        public Square(double side) {
            this.side = side;
        }

        @Override
        public double getArea() {
            return side * side;
        }
    }
}
