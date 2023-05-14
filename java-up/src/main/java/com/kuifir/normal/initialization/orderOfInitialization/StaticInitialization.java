package com.kuifir.normal.initialization.orderOfInitialization;

/**
 * 静态数据的初始化
 * 无论创建了多少对象，静态数据都只有一份存储空间。
 * static关键字不能用于局部变量，而仅适用于（字段）成员变量。
 * 如果一个字段是static的基本类型，并且没有初始化，那它就会获得基本类型的标准初始值。
 * 如果它是一个对象引用，则默认初始值为null。
 * 如果将静态数据的初始化放在定义时，采取的方式和非静态变量没有什么不同。
 * <p>
 *
 * @author kuifir
 * @date 2023/5/14 13:34
 * @see OrderOfInitialization
 * @see StaticInitialization
 */
class Bowl {
    Bowl(int marker) {
        System.out.println("Bowl(" + marker + ")");
    }

    void f1(int marker) {
        System.out.println("f1(" + marker + ")");
    }
}

class Table {
    static Bowl bowl = new Bowl(1);

    Table() {
        System.out.println("Table()");
        bowl2.f1(1);
    }

    void f2(int marker) {
        System.out.println("f2(" + marker + ")");
    }

    static Bowl bowl2 = new Bowl(2);
}

class Cupboard {
    Bowl bowl3 = new Bowl(3);
    static Bowl bowl4 = new Bowl(4);

    Cupboard() {
        System.out.println("Cupboard()");
        bowl4.f1(2);
    }

    void f3(int marker) {
        System.out.println("f3(" + marker + ")");
    }

    static Bowl bowl5 = new Bowl(5);
}

/**
 * 演示static何时被初始化。
 * Bowl 代码里的提示信息揭示了一个类的创建过程,Table和Cupboard类把Bowl类型的静态字段分散到各处定义。
 * 注意Cupboard类还在static定义之前创建了一个非静态的bowl3.
 * <p></p>
 * 输出显示了static初始化仅在必要时发生。
 * 如果不创建Table对象并且也不引用Tale.bowl1或table.bowl2,则Bowl类型的bowl1和bowl2永远不会被创建。
 * 它们仅在第一个Table对象创建（或第一次访问静态数据）时被初始化，之后这些静态对象不会被重新初始化。
 * <p></p>
 * 初始化的顺序是从静态字段开始（如果它们还没有被先前的对象创建触发初始化的话），然后是非静态字段。
 * 可以从输出中看到这一点。
 * 要执行静态的main()方法，必须先加载StaticInitialization类，然后初始化他的静态字段table和cupboard,
 * 这会导致它们对应的类被加载，并且因为他们都包含了静态的Bowl对象，所以Bowl也被加载。
 * 因此，这个特定程序中的所有类都在main()方法开始执行前加载。
 * 通常情况下并非如此，这是因为常见的程序中不会像此例那样通过static将所有内容连接在一起。
 * <p></p>
 * 为了总结对象创建的过程,假设有一个名为Dog的类.
 * 1. 尽管没有显示使用static关键字,但构造器实际上也是静态方法.
 * 因此,第一次创建类型为Dog的对象时,或者第一次访问类Dog的静态方法或静态字段时,Java解释器会搜索类路径来定位Dog.class文件.
 * 2. 当Dog.class被加载后(这将创建一个Class对象),它的所有静态初始化工作都会执行.因此,静态初始化只在Class对象首次加载时发生一次.
 * 3. 当使用new Dog()创建对象时,构建过程首先会在堆上为Dog对象分配足够的存储空间,
 * 4. 这块存储空间会被清空,然后自动将该Dog对象中的所有基本类型设置为其默认值(数值类型默认值时0.boolean和char则是和0等价的对应值),而引用会被设置为null.
 * 5. 执行所有出现字段定义出的初始化操作.
 * 6. 执行构造器.这实际上可能设计相当多的动作,尤其是在涉及继承时.
 * @author kuifir
 * @see Bowl
 * @see OrderOfInitialization
 * @see ExplicitStatic
 */
public class StaticInitialization {
    public static void main(String[] args) {
        System.out.println("main creating new Cupboard");
        new Cupboard();
        System.out.println("main creating new Cupboard");
        new Cupboard();
        table.f2(1);
        cupboard.f3(1);
        // Bowl(1)
        // Bowl(2)
        // Table()
        // f1(1)
        // Bowl(4)
        // Bowl(5)
        // Bowl(3)
        // Cupboard()
        // f1(2)
        // main creating new Cupboard
        // Bowl(3)
        // Cupboard()
        // f1(2)
        // main creating new Cupboard
        // Bowl(3)
        // Cupboard()
        // f1(2)
        // f2(1)
        // f3(1)

    }

    static Table table = new Table();
    static Cupboard cupboard = new Cupboard();
}
