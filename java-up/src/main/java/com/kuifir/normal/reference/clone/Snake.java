package com.kuifir.normal.reference.clone;

/**
 * 测试克隆，看看是否引用的所有目标都被可克隆了：
 * Object.clone()的效果：
 * 调用Object.clone()时究竟发生了些什么，以至于在重写clone()时调用super.clone()的这一步如此重要呢？
 * 根类中的clone()方法负责创建正确大小的存储空间，并执行了从原始对象中的所有二进制位到新对象存储中的按位复制。
 * 也就是说，该方法并不只是创建存储空间和复制一个Object,
 * 它实际上会判断要复制的实际对象（不只是基类对象，还包括派生对象）的大小，然后再进行复制。
 * 所有这些都依靠在根类中定义的clone()方法的代码来实现，而根类并不知道具体那个对象会被继承，因此你可以猜到，
 * 这个过程中用到了反射来确定要克隆的实际对象。这样，clone()方法就可以创建大小合适的存储空间，并正确地对该类型执行按位复制。
 * <p></p>
 * 克隆过程通常应该从调用super.clone()开始，这样便通过创建精确的副本，为克隆操作奠定了基础。
 * 然后就可以执行所需的其他操作来完成克隆了。
 * 要清楚的知道其他操作是什么，就需要理解Object.clone()到底给你带来了什么好处，
 * 特别是该方法是否会自动克隆所有引用指向的目标。我们可以通过测试来了解：
 *<p></p>
 * 一条{@link Snake}有多个段组成，每一段都是Snake类型。因此这是一个单向链表。
 * 这些段通过递归的方式创建，每一段都会对第一个构造器参数做递减操作，直到值为0。
 * 为给每一段都定义一个唯一标识，第二个char类型的参数会在每次递归调用构造方法时递增。
 * increment()方法递归地对标识自增，以体现出变化，而toString()则递归地打印出每一个标识。
 * 从结果可以看出，只有第一段被Object.clone()复制了，因此他执行的是浅拷贝。
 * 如果想要复制整条蛇，也就是深拷贝，你需要在重写的clone()中执行额外的操作。
 * <p></p>
 * 在任何派生自可克隆类的类中，你通常需要调用super.clone()，以确保所有积累的操作(包括Object.clone())都执行了。
 * 然后对对象中的所有引用都显示地执行clone()，否则这些引用都将只是原始对象中对应引用的别名。
 * 这和调用构造器的方式相似：最开始调用积累的构造器，然后是下一层派生的构造器，以此类推，直到最后一层派生的构造器。
 * 区别在于，clone()并不是构造器，因此无法自动实现这种调用机制，你必须确保自己实现这种机制。
 *
 * @author kuifir
 * @date 2023/6/5 23:05
 */
public class Snake implements Cloneable {
    private Snake next;
    private char c;

    /**
     * i的值 == 蛇身的段数
     */
    public Snake(int i, char x) {
        c = x;
        if (--i > 0) {
            next = new Snake(i, (char) (x + 1));
        }
    }

    public void increment() {
        c++;
        if (next != null) {
            next.increment();
        }
    }

    @Override
    public String toString() {
        String s = ":" + c;
        if (next != null) {
            s += next.toString();
        }
        return s;
    }

    @Override
    public Snake clone() {
        // 只有第一段被Object.clone()复制了，因此执行的是浅拷贝。
        try {
            return (Snake) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Snake s = new Snake(5, 'a');
        System.out.println("s = " + s);
        Snake s2 = s.clone();
        System.out.println("s2 = " + s2);
        s.increment();
        // 只有第一段被Object.clone()复制了，因此执行的是浅拷贝。
        System.out.println("after s.increment(), s2 = " + s2);

    }
}
