package com.kuifir.normal.reference.clone;

/**
 * 为类增加可克隆能力
 * <p></p>
 * 尽管clone()是在所有类的基类Object中定义的，但并不是所有的类就因此自动具备克隆能力了。
 * 这看起来似乎有点反常,理论上基类中的方法应该在子类中也是可以调用的。
 * Java中的克隆确实违反了这个概念，如果你希望某个类具备克隆能力，就必须添加特殊的代码来使其生效。
 * <ul>
 *     <li>利用protected的技巧</li>
 *     <li>实现Cloneable接口</li>
 * </ul>
 * <p></p>
 * 1. 利用protected的技巧
 *     为了避免你创建的所有类都具有默认的的可克隆能力，基类Object中的clone()方法是protected的。
 * 这意味着对于那些只是简单使用某个类（并不继承出子类）的调用方程序员来说，可克隆能力并不是默认可用的。
 * 这同时意味着，你无法通过基类的引用来调用clone()。这实际上是在编译期给你一个信号，让你知道你的对象是不可克隆的，
 * 并且奇怪的是，Java标准库中的大部分类是不可克隆的。
 * 因此如果你声明：<pre>{@code
 * Integer x = 1;
 * x = x.clone()
 * }</pre>
 *     你就会在编译期得到一条错误消息，指出没有调用clone()方法的权限(Integer并未重写该方法，该方法仍然是默认的protected版本)。
 * 然而，如果是在一个Object的子类（就像所有的类一样）中，那么你就有调用Object.clone()的权限，因为该方法时protected的，而你是继承者
 * 基类中的clone()的功能很有用，他会执行实际<b>子类对象</b>的按位复制,因此充当了公用的克隆操作。不过为了你的克隆操作可被访问，
 * 你必须将它设为public的。因此，在你克隆时，有两个关键步骤：
 * <ul>
 *     <li>调用super.clone</li>
 *     <li>将你的克隆操作设为public的</li>
 * </ul>
 * 你很可能会在后续的子类中重写clone()方法，否则执行的将是你现有的clone()（现在是public的了），这或许并未你想要的操作
 * （虽然由于Object.clone()会创建实际的对象副本，它也有可能就是你想要的操作）。protected技巧只有一次使用时机，即
 * 你首次继承一个不具备可克隆能力的类，且希望继承出的类具备可克隆性时。对于从该继承类再度继承出的任何类，clone()方法都是可用的了。
 * 因为在Java中，是不可能在派生过程中缩小方法访问权限的。也就是说一旦一个类成为可克隆的，一切从其派生的类就都是可克隆的，
 * 除非你使用了语言提供的某种机制来"关闭"克隆能力。{@link }
 * <p></p>
 * 2.实现Cloneable接口{@link Cloneable}
 * 要让对象实现完整的可克隆能力，还需要进一步操作：实现{@link Cloneable}接口。这是一个空（标记）接口
 * Cloneable接口的存在有两个原因。第一个原因是，你可能有个对某父类型的向上转型的使用，但并不知道是否可以克隆该对象。
 * 这时可以使用instanceof关键字来找出该引用是否和某个可克隆的对象有关联：<pre>{@code
 * if(myReference instanceof Cloneable) // ...
 * }
 * </pre>
 * 第二个原因是，可克隆能力的设计背后有一种考虑，即可能你并不希望所有类型的对象都是可克隆的。
 * 因此Object.clone()可以验证一个类是否实现了Cloneable接口，如果没有，便会抛出{@link CloneNotSupportedException}异常。
 * 所以一般来说，你是被迫加上implement Cloneable的，以作为支持克隆的必要操作。
 *
 * @author kuifir
 * @date 2023/6/4 23:02
 */
public class LocalCopy {
    public static Duplo g(Duplo v){
        // 传递引用，修改了外部的对象。
        v.increment();
        return v;
    }
    public static Duplo f(Duplo v){
        // 本地副本
        v = v.clone();
        v.increment();
        return v;
    }

    public static void main(String[] args) {
        Duplo a = new Duplo(11);
        Duplo b = g(a);
        // 引用相等，并不是对象相等。
        System.out.println("a==b: " + (a == b) + "\na= " + a + "\nb= " + b);
        Duplo c = new Duplo(47);
        Duplo d = f(c);
        System.out.println("c==d: " + (c == d) + "\nc= " + c + "\nd= " + d);

    }
}

class Duplo implements Cloneable {
    private int n;

    Duplo(int n) {
        this.n = n;
    }

    @Override
    public Duplo clone() {
        try {
            return (Duplo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return n;
    }

    public void setValue(int n) {
        this.n = n;
    }

    public void increment() {
        n++;
    }

    @Override
    public String toString() {
        return Integer.toString(n);
    }
}