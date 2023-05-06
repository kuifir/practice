package com.kuifir.normal.innerClass;

/**
 * 想让其他某个对象来创建它的某个内部类的对象，可以使用.new语法，
 * 在new表达式中提供指向其他外部类对象的引用.
 * @author kuifir
 */
public class DotNew {
    public static void main(String[] args) {
        DotNew dotNew = new DotNew();
        // 要使用外部类的对象来创建内部类的对象，这也解决了内部类的名字作用域问题，
        // 所以不用dotNew.new DotNew.Inner()（确实也不能用）
        DotNew.Inner inner = dotNew.new Inner();
    }
    public class Inner{
    }
}
