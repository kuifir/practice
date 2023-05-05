package com.kuifir.iterators;

/**
 * 迭代器设计模式
 *
 * 当创建一个内部类时，这个内部类的对象中会隐含一个链接，指向用于创建该对象的外围对象。
 * 通过该链接，无需任何特殊条件，内部类对象就可以访问外围对象的成员。
 * 此外，内部类拥有对外围对象所有元素的人访问权。
 *
 * @author kuifir
 */
interface Selector {
    boolean end();

    Object current();

    void next();
}

public class Sequence {
    private Object[] items;
    private int next = 0;

    public Sequence(int size) {
        items = new Object[size];
    }

    public void add(Object x) {
        if (next < items.length) {
            items[next++] = x;
        }
    }

    public Selector selector() {
        return new SequenceSelector();
    }

    private class SequenceSelector implements Selector {
        private int i = 0;

        @Override
        public boolean end() {
            return i == items.length; // 可以直接访问外部类Sequence的元素
        }

        @Override
        public Object current() {
            return items[i];
        }

        @Override
        public void next() {
            if (i < items.length) {
                i++;
            }
        }
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence(10);
        for (int i = 0; i < 10; i++) {
            sequence.add(i);
        }
        Selector selector = sequence.selector();
        while (!selector.end()) {
            System.out.println(selector.current());
            selector.next();
        }
    }
}
