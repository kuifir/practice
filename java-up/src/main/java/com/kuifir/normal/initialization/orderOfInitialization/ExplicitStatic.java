package com.kuifir.normal.initialization.orderOfInitialization;



class Cup {
    Cup(int marker) {
        System.out.println("Cup(" + marker + ")");
    }

    void f(int marker) {
        System.out.println("f(" + marker + ")");
    }
}
class Cups{
    static Cup cup1;
    static Cup cup2;
    static {
        cup1 = new Cup(1);
        cup2 = new Cup(2);
    }
    Cups(){
        System.out.println("Cups()");
    }

}
/**
 * 显式的静态初始化
 * Java 允许在一个类里将多个静态初始化语句放在一个特殊的"静态子句"里(有时称为静态块)
 * 尽管有点像一个方法,但它只是在static关键字后加了一段代码.
 * 这段代码和其他静态初始化语句一样,只执行一次:第一次创建该类的对象时,或第一次访问该类的静态成员时(即使从未创建过该类的对象).
 *
 * 静态子句的显示静态初始化
 * @author kuifir
 * @date 2023/5/14 14:50
 * @see StaticInitialization
 * @see ExplicitStatic
 */
public class ExplicitStatic {
    public static void main(String[] args) {
        System.out.println("Inside main()");
        Cups.cup1.f(99);
    }
    static Cups cups1 = new Cups();
    static Cups cups2 = new Cups();
}
