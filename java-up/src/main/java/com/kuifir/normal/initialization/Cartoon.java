package com.kuifir.normal.initialization;

/**
 * 继承时调用构造器
 * 子类初始化:
 * 在创建子类对象时,它里面包含了一个基类的子对象.
 * 这个子对象与直接通过基类创建的对象是一样的.
 * 只是从外面看,基类的子对象被包裹在了子类的对象中.
 * <p></p>
 * 正确初始化基类的子对象至关重要,我们只有一种方法可以保证这点:
 * 在子类构造器中调用基类构造器来执行初始化,它具有执行基类初始化所需的全部信息和权限.
 * Java会自动在子类构造器中插入对基类构造器的调用.
 * <p></p>
 * 构造过程是从基类"向外"进行的,因此基类在子类构造器可以访问它之前就被初始化了.
 * 即使没有为Cartoon创建构造器,编译器也会为它合成一个可以调用基类构造器的无参构造器.
 *
 * @see com.kuifir.normal.initialization.orderOfInitialization.OrderOfInitialization
 * @author kuifir
 * @date 2023/5/14 17:46
 */
public class Cartoon extends Drawing{
    public Cartoon(){
        System.out.println("Cartoon constructor");
    }

    public static void main(String[] args) {
        Cartoon cartoon = new Cartoon();
        // Art constructor
        // Drawing constructor
        // Cartoon constructor
    }
}
class Art{
    Art(){
        System.out.println("Art constructor");
    }
}
class Drawing extends Art{
    Drawing(){
        System.out.println("Drawing constructor");
    }
}