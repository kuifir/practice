package com.kuifir.normal.innerClass.BigEgg;

/**
 * 内部类可以被重写吗
 * 先在某个外围类中创建一个内部类，然后新创建一个类，使其继承该外围类，并在其中重新定义之前的内部类，这会发生什么呢？
 * 换句话说，是否可能重写整个内部类？
 * 这看上去像一个很强大的概念，但是把内部当成外围类中的其他方法一样重写，并没有什么实际意义。
 *<p/>
 * 无参构造器是由编译器自动合成的，这里调用了基类的无参构造器。你可能会认为，因为创建了一个BigEgg,所以应该使用Yolk
 * 的"重写"版本，但事实并非如此，正如程序输出的那样。
 * 当继承外围类是，内部类并没有额外的特殊之处。这两个内部类是完全独立的实体。分别在自己的命名空间中。
 * 然而显式地继承某个内部类也是可以的{@link BigEgg2}
 * @author kuifir
 * @date 2023/5/7 23:38
 */
public class BigEgg extends Egg {
    protected class Yolk {
        public Yolk() {
            System.out.println("BigEgg.Yolk()");
        }
    }

    public static void main(String[] args) {
        // New Egg()
        // Egg.Yolk()
        new BigEgg();
    }
}

class Egg {
    private Yolk y;

    protected class Yolk {
        public Yolk() {
            System.out.println("Egg.Yolk()");
        }
    }

    Egg() {
        System.out.println("New Egg()");
        y = new Yolk();
    }
}
