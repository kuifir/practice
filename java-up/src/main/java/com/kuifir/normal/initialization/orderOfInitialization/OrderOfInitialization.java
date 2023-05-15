package com.kuifir.normal.initialization.orderOfInitialization;

/**
 * 初始化顺序：
 * 类中的变量定义顺序决定了初始化的顺序。
 * 即使分散到方法定义之间，变量定义仍然会在任何方法（包括构造器）调用之前就被初始化。
 * 初始化全过程{@link Beetle}
 * 演示初始化顺序{@link OrderOfInitialization}
 * 静态初始化{@link StaticInitialization}
 * 静态子句的显示静态初始化{@link ExplicitStatic}
 * 非静态实例初始化{@link Mugs}
 * 子类的初始化{@link com.kuifir.normal.initialization.Cartoon}
 * 构造器调用顺序(有多态){@link com.kuifir.normal.polymorphism.Sandwich}
 * <p></p>
 * 总结:
 * 对象创建的过程: {@link StaticInitialization}
 * 构造器调用顺序: {@link com.kuifir.normal.polymorphism.Sandwich}
 * @see Beetle
 * @see com.kuifir.normal.polymorphism.Sandwich
 * @see OrderOfInitialization
 * @see StaticInitialization
 * @see ExplicitStatic
 * @see Mugs
 * @see com.kuifir.normal.initialization.Cartoon
 * @author kuifir
 * @date 2023/5/14 13:18
 */
public class OrderOfInitialization {
    public static void main(String[] args) {
        House house = new House();
        // 提示构造过程结束
        house.f();
        // Window(1)
        // Window(2)
        // Window(3)
        // House()
        // Window(33)
        // f()
    }
}
/**
 * 当调用构造器来创建Window对象时会看到一个信息
 */
class Window {
    Window(int marker) {
        System.out.println("Window(" + marker + ")");
    }
}
class House{
    /**
     * 定义在构造器之前
     */
    Window w1 = new Window(1);
    House(){
        // 提示已经在构造器里
        System.out.println("House()");
        // 重新初始化w3
        w3 = new Window(33);
    }
    /**
     * 定义在构造器之后
     */
    Window w2 = new Window(2);
    void f(){
        System.out.println("f()");
    }
    /**
     *　定义在尾部
     */
    Window w3 = new Window(3);

}



