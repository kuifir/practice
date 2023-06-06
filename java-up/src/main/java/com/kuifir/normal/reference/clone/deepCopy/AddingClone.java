package com.kuifir.normal.reference.clone.deepCopy;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 将浅拷贝实现{@link com.kuifir.normal.reference.clone.CloneArrayList}
 * 改为深拷贝
 *
 * @author kuifir
 * @date 2023/6/6 21:54
 * @see com.kuifir.normal.reference.clone.CloneArrayList
 */
public class AddingClone {
    public static void main(String[] args) {
        Int2 x = new Int2(10);
        Int2 x2 = x.clone();
        x2.increment();
        System.out.println("x = " + x + ", x2= " + x2);

        // 继承出的任何事物同样也是可克隆的
        Int3 x3 = new Int3(7);
        Int3 x4 = (Int3) x3.clone();
        x3.increment();
        x3.j =10;
        System.out.println("x3 = " + x3 + ", x4= " + x4);

        ArrayList<Int2> v = IntStream.range(1, 10)
                .mapToObj(Int2::new)
                .collect(Collectors.toCollection(ArrayList::new));
        // 直接调用ArrayList的clone()是浅拷贝
        ArrayList<Int2> v2 = (ArrayList<Int2>) v.clone();
        v2.forEach(Int2::increment);
        System.out.println("v: " + v);
        System.out.println("v2: " + v2);
        // 现在克隆每个元素(实现深拷贝)
        IntStream.range(0, v.size()).forEach(i -> v2.set(i, v.get(i).clone()));
        // 对v2的所有元素进行累加
        v2.forEach(Int2::increment);
        System.out.println("v2: " + v2);
        // 看看是否改变了v中所有元素
        System.out.println("v: " + v);

    }
}

class Int2 implements Cloneable {
    private int i;

    public Int2(int i) {
        this.i = i;
    }

    public void increment() {
        i++;
    }

    @Override
    protected Int2 clone() {
        try {
            return (Int2) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(i);
    }
}

/**
 * 继承不会移除可克隆性
 */
class Int3 extends Int2 {
    /**
     * 自动创建了副本,不需要重写clone()，也能完成克隆。只要不增加需要克隆的引用。
     */
    int j;

    public Int3(int i) {
        super(i);
    }

    @Override
    public String toString() {
        return super.toString() + ",j= " + j;
    }
}
