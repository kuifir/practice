package com.kuifir.normal.polymorphism;

/**
 * 构造器调用顺序
 * 基类的构造器总是在子类的构造过程中被调用.初始化会在继承层次结构里自动向上移动,因此每个基类的构造器都会被调用.
 * 这是有道理的,因为构造器有一项特殊的工作:确保对象的正确构建.
 * 字段通常是private的,因此一般必须假设子类只能访问自己的成员,而不能访问基类的成员.
 * 只有基类构造器具有足够的信息和权限来初始化它自己的成员.
 * 因此必须调用所有构造器.否则将无法正确构造整个对象.
 * 这就是编译器会对子类的每个构造部分强制执行基类构造器的原因.
 * 如果你没有在子类构造器代码中显式调用基类构造器,它将显式调用基类的无参构造器.如果没有无参构造器,编译器会报错.
 * (在一个类没有任何构造器的情况下,编译器会为他自动合成一个无参构造器)
 * <p></p>
 * {@link Sandwich}输出显式,一个复杂对象的构造器调用顺序如下:
 * 1. 基类的构造器被调用,递归地重复此步骤,一直到构造层次结构的跟.根类先被构造,然后是下一个子类,以此类推,直到最底层的字类.
 * 2. 然后按照声明的顺序来初始化成员.
 * 3. 最后执行子类构造器的方法体.
 * <p></p>
 * 构造器的调用顺序很重要.继承时,你已经知道了基类的所有信息,并且可以访问基类的任何public和protected成员.
 * 这意味着在子类中,你可以假设基类的所有成员都是有效的.
 * 在一个正常的方法中,构造肯定已经发生了,所以对象所有部分的所有成员也都被构造好了.
 * <p></p>
 * 在构造器中,你必须能确定所有成员都已构建.保证这一点的唯一方法是首先调用基类的构造器.
 * 这样,当子类构造器中时,基类中可以访问的所有成员都已经初始化了.
 * 为了让所有成员在构造器内都是有效的,只要有可能,你就应该在类中的定义处来初始化所有的成员对象,这些成员通过组合引入到了类里.
 * 如果你遵循了这种做法,就会有助于确保当前对象的所有基类成员和自身的成员对象都正常初始化.
 * 不幸的是这并不能包括所有的情况.{@link }
 * @author kuifir
 * @date 2023/5/14 23:33
 */
public class Sandwich extends PortableLunch{
    private Bread b = new Bread();
    private Cheese c = new Cheese();
    private Lettuce l = new Lettuce();
    public Sandwich(){
        System.out.println("Sandwich()");
    }

    public static void main(String[] args) {
        new Sandwich();
        // 结果
        // Meal() 一餐
        // PortableLunch() 便携式的午餐
        // Bread() 面包
        // Cheese() 干酪
        // Lettuce() 莴苣
        // Sandwich()

    }
}
class Meal{
    Meal(){
        System.out.println("Meal() 一餐");
    }
}
class Bread{
    Bread(){
        System.out.println("Bread() 面包");
    }
}
class Cheese{
    Cheese(){
        System.out.println("Cheese() 干酪");
    }
}
class Lettuce{
    Lettuce(){
        System.out.println("Lettuce() 莴苣");
    }
}
class Lunch extends Meal{
    Lunch(){
        System.out.println("Lunch()");
    }
}
class PortableLunch extends Lunch{
    PortableLunch(){
        System.out.println("PortableLunch() 便携式的午餐");
    }
}