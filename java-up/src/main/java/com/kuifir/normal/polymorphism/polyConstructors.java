package com.kuifir.normal.polymorphism;

/**
 * 构造器和多态
 * 本例不会生成期望的结果
 * 构造器调用的层次结构带来了一个难题,对于正在构造的对象,如果在构造器中调用他的动态绑定方法,会发生什么?
 * 在普通方法内部,动态绑定调用是在运行时解析的,这是因为对象步知道它是属于该方法所在的类还是其子类.
 * 如果在构造器中调用动态绑定方法,就会用到该方法被重写后的定义.
 * 但是这个调用的效果可能相当出乎意料,因为这个被重写的方法是在对象完全构造之前被调用的.这可能会带来一些难以发现的错误.
 * <p></p>
 * 只有基类中的final方法可以在构造器安全调用(这也适用于private方法,它们默认就是final的).
 * 这些方法不能被重写,因此不会产生这种令人惊讶的问题.你可能并不能总是遵循此准则,但是应该朝这个方向努力.
 * @author kuifir
 * @date 2023/5/15 0:36
 */
public class polyConstructors {
    public static void main(String[] args) {
        new RoundGlyph(5);
        // 结果:(45 行注释放开后会报错)
        // Glyph() before draw()
        // RoundGlyph.draw(), radius = 0
        // Glyph() after draw()
        // RoundGlyph.RoundGlyph(), radius = 5
    }
}

class Glyph {
    Integer a ;
    void draw(){
        System.out.println("Glyph.draw()");
    }
    Glyph(){
        System.out.println("Glyph() before draw()");
        draw(); // new 子类时,虽然会调用基类构造器,但是基类构造器中调用的方法 会调用 子类重写的方法,此时子类的字段还没有初始化,如果调用了引用各类型的方法会报错.
        System.out.println("Glyph() after draw()");
    }

}
class RoundGlyph extends Glyph{
    private int radius = 1;
    RoundGlyph(int r){
        radius = r;
        System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
    }

    @Override
    void draw() {
//        a ++ ;
        System.out.println("RoundGlyph.draw(), radius = " + radius);
    }
}
