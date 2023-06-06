package com.kuifir.normal.reference.clone.deepCopy;

/**
 * 克隆组合对象
 * 在你试图深拷贝组合对象时，会遇到一个问题。
 * 你必须假设所有成员对象中的clone()方法都会按顺序对各自的引用执行深拷贝，并照此进行下去。
 * 这一点是必须确保的。它实际上意味着为了正确执行深拷贝，你要么需要控制所有的类的所有代码，
 * 要么至少对深拷贝涉及的所有类都有足够了解，以确定他们都能正确地执行各自的深拷贝。
 *{@link OceanReading}
 *
 * @see OceanReading
 * @author kuifir
 * @date 2023/6/6 21:07
 */
public class DepthReading implements Cloneable {
    private double depth;

    public DepthReading(double depth) {
        this.depth = depth;
    }

    @Override
    protected DepthReading clone() {
        try {
            return (DepthReading) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return String.valueOf(depth);
    }
}
