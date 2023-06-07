package com.kuifir.normal.reference;

/**
 * 不可变性的缺点
 * 创建不可变类，乍一看似乎为我们提供了一种优雅的解决方案。
 * 不过每当你确实需要修改这种新类型的对象时，就必须忍受创建新对象的开销，并可能导致更频繁的垃圾收集。
 * 对有些类来说，这不是问题（而且函数式编程语言以来这种设计）。
 * 但是对于另一些类（如String类）来说，这种开销是非常昂贵的-牢记格言，切勿过早优化。
 * 解决的办法是创建一个可被修改的伴生类。然后如果你要做很多的修改工作，
 * 就可以切换使用该可被修改的伴生类，并在完成后切换回不可变类。
 *
 * @author kuifir
 * @date 2023/6/7 22:28
 */
public class Immutable2 {
    private final int data;

    public Immutable2(int initValue) {
        this.data = initValue;
    }

    public int read() {
        return data;
    }

    public boolean nonzero() {
        return data != 0;
    }

    public Immutable2 add(int x) {
        return new Immutable2(data + x);
    }

    public Immutable2 multiply(int multiplier) {
        return new Immutable2(data * multiplier);
    }

    public Mutable makeMutable() {
        return new Mutable(data);
    }

    public static Immutable2 modify1(Immutable2 y) {
        Immutable2 val = y.add(12);
        val = val.multiply(4);
        val = val.add(11);
        val = val.multiply(2);
        return val;
    }

    /**
     * 这会得到相同的结果
     * @param y
     * @return
     */
    public static Immutable2 modify2(Immutable2 y) {
        Mutable m = y.makeMutable();
        m.add(12).multiply(4).add(11).multiply(2);
        return m.makeImmutable2();
    }

    public static void main(String[] args) {
        Immutable2 i2 = new Immutable2(47);
        Immutable2 r1 = modify1(i2);
        Immutable2 r2 = modify2(i2);
        System.out.println("i2 = " + i2.read());
        System.out.println("r1 = " + r1.read());
        System.out.println("r2 = " + r2.read());
    }
}

class Mutable {
    private int data;

    public Mutable(int data) {
        this.data = data;
    }

    public Mutable add(int x) {
        data += x;
        return this;
    }

    public Mutable multiply(int x) {
        data *= x;
        return this;
    }

    public Immutable2 makeImmutable2() {
        return new Immutable2(data);
    }
}