package com.kuifir.normal.reference.clone.deepCopy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 复制构造器
 * 尝试对一个对象创建相同类型的本地副本的构造器。
 * 克隆操作的构建过程可能会十分复杂，是否还有其他解决方案呢？
 * 一种（很慢的方案是使用序列化。{@link Compete}
 * 另一种方案时实现一个专门用于创建对象副本的构造器。
 * 在C++中，这叫做<b>复制构造器</b>（copy constructor,又称拷贝构造器，即会创建对象副本的构造器。）
 * <p></p>
 * 下面进行第一次尝试，看起来似乎应该有效，但是没有成功：
 * 复制构造器使C++的一个重要组成部分，因为它为对象自动创建了本地副本。
 * 然而之前的示例表明，这对Java来说行不通。这是为什么呢？
 * 在Java中，我们操纵的一些都是引用，但在C++中，你可以操作类似引用的实体，也可以直接传递对象。
 * 这就是C++的复制构造器的目的：接收对象并将其按值传递，从而复制该对象。
 * 因此在C++中它可以正常工作，但是要记住这种方法在Java中会失败，所以不要使用。
 * @author kuifir
 * @date 2023/6/7 21:17
 * @see Compete
 */
public class CopyConstructor {
    public static void ripen(Tomato t) {
        // 使用复制构造器
        t = new Tomato(t);
        System.out.println("In ripen, t is a " + t.getClass().getSimpleName());
    }

    public static void slice(Fruit f) {
        // 这样行得通吗？
        f = new Fruit(f);
        System.out.println("In slice, f is a " + f.getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public static void ripen2(Tomato t) {
        try {
            Class<? extends Tomato> c = t.getClass();
            Constructor<? extends Tomato> ct = c.getConstructor(new Class[]{c});
            Object obj = ct.newInstance(new Object[]{t});
            System.out.println("In ripen2, t is a " + obj);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            System.out.println(e);
        }
    }

    public static void slice2(Fruit f) {
        try {
            Class<? extends Fruit> c = f.getClass();
            Constructor<? extends Fruit> constructor = c.getConstructor(new Class[]{c});
            Object obj = constructor.newInstance(new Object[]{f});
            System.out.println("In slice2, f is a " + obj.getClass().getSimpleName());
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Tomato tomato = new Tomato();
        ripen(tomato);
        slice(tomato);
        ripen2(tomato);
        slice2(tomato);
        GreenZebra g = new GreenZebra();
        ripen(g);
        slice(g);
        ripen2(g);
        slice2(g);
        g.evaluate();
    }
}

class FruitQualities {
    private int weight;
    private int color;
    private int firmness;
    private int ripeness;
    private int smell;

    /**
     * 无参构造器
     */
    public FruitQualities() {
    }

    /**
     * 复制构造器
     */
    FruitQualities(FruitQualities f) {
        weight = f.weight;
        color = f.color;
        firmness = f.firmness;
        ripeness = f.ripeness;
        smell = f.smell;
    }
}

class Seed {
    /**
     * 无参构造器
     */
    public Seed() {
    }

    /**
     * 复制构造器
     *
     * @param s
     */
    public Seed(Seed s) {

    }

}

class Fruit {
    private FruitQualities fq;
    private int seeds;
    private Seed[] s;

    Fruit(FruitQualities q, int seedCount) {
        fq = q;
        seeds = seedCount;
        s = new Seed[seedCount];
        for (int i = 0; i < seeds; i++) {
            s[i] = new Seed();
        }
    }

    /**
     * 复制构造器
     */
    Fruit(Fruit f) {
        fq = new FruitQualities(f.fq);
        seeds = f.seeds;
        s = new Seed[seeds];
        // 调用所有的Seed复制构造器
        for (int i = 0; i < seeds; i++) {
            s[i] = new Seed(f.s[i]);
        }
    }

    /**
     * 这样可以使派生的构造器（或其他方法）放入不同的qualities(品质参数)
     */
    protected void addQualities(FruitQualities q) {
        fq = q;
    }

    protected FruitQualities getQualities() {
        return fq;
    }
}

class Tomato extends Fruit {
    Tomato() {
        super(new FruitQualities(), 100);
    }

    /**
     * 复制构造器
     *
     * @param t
     */
    Tomato(Tomato t) {
        // 向上转型为父复制构造器
        super(t);
    }
}

class ZebraQualities extends FruitQualities {
    private int stripedness;

    public ZebraQualities() {
        super();
    }

    ZebraQualities(ZebraQualities z) {
        super(z);
        stripedness = z.stripedness;
    }
}

class GreenZebra extends Tomato {
    GreenZebra() {
        addQualities(new ZebraQualities());
    }

    GreenZebra(GreenZebra g) {
        // 调用Tomato(Tomato)
        super(g);
        // 恢复正确的qualities
        addQualities(new ZebraQualities());
    }

    public void evaluate() {
        ZebraQualities zq = (ZebraQualities) getQualities();
    }
}