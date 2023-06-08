package com.kuifir.normal.collectionTopic;

import java.util.AbstractList;
import java.util.List;

/**
 * 使用享元自定义Collection
 * 当普通的解决方案需要太多对象时，或者当生成正常的对象会占用太多空间时，就可以使用享元。
 * 享元模式将对象的一部分外化了：我们不再将对象的所有内容至于该对象之内，
 * 而是通过一个更高效的外部表来查找部分或整个对象（或者通过其他一些可以节省空间的计算来生成）
 * <p></p>
 * 下面定义了一个可以为任意大小的List,从效果上看，它相当于用Integer预先初始化了。
 * 要从AbstractList创建一个只读的List，必须实现get()和size()两个方法：
 * <p></p>
 * 这是享元模式的一个很好的示例。get()会在我们请求时“计算”值，所以并没有需要存储和初始化的实际底层List结构。
 * 在大多数程序中，这里节省的存储空间不会产生什么影响。然而，它允许我们使用一个非常大的index来调用List.get(),
 * 不需要一个填充好所有这些值的List。另外，我们可以在自己的程序中使用大量的CountingIntegerList,
 * 而不用担心存储问题。确实，享元的一大好处就是让我们无须考虑资源问题就能使用更好的抽象。
 * <p></p>
 * 我们可以使用享元来实现其他“已初始化‘的、数据集大小不限的自定义集合。
 * 下面是一个Map,它会为每个Integer键生成一个独一无二的值。{@link CountMap}
 * @author kuifir
 * @date 2023/6/8 22:17
 */
public class CountingIntegerList extends AbstractList<Integer> {
    private int size;

    public CountingIntegerList() {
        size = 0;
    }

    public CountingIntegerList(int size) {
        this.size = Math.max(size, 0);
    }

    @Override
    public Integer get(int index) {
        return index;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        List<Integer> cil = new CountingIntegerList(30);
        System.out.println(cil);
        System.out.println(cil.get(500));
    }
}
