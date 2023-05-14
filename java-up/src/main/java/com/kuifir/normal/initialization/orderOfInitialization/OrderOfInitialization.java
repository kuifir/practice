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
 * <p></p>
 * 为了总结对象创建的过程,假设有一个名为Dog的类.
 * 1. 尽管没有显示使用static关键字,但构造器实际上也是静态方法.
 * 因此,第一次创建类型为Dog的对象时,或者第一次访问类Dog的静态方法或静态字段时,Java解释器会搜索类路径来定位Dog.class文件.
 * 2. 当Dog.class被加载后(这将创建一个Class对象),它的所有静态初始化工作都会执行.因此,静态初始化只在Class对象首次加载时发生一次.
 * 3. 当使用new Dog()创建对象时,构建过程首先会在堆上为Dog对象分配足够的存储空间,
 * 4. 这块存储空间会被清空,然后自动将该Dog对象中的所有基本类型设置为其默认值(数值类型默认值时0.boolean和char则是和0等价的对应值),而引用会被设置为null.
 * 5. 执行所有出现字段定义出的初始化操作.
 * 6. 执行构造器.这实际上可能设计相当多的动作,尤其是在涉及继承时.
 * @see Beetle
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



