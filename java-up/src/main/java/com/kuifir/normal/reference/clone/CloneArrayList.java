package com.kuifir.normal.reference.clone;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * clone()实在{@link Object}中定义的，并且访问权限为protected。
 * 你需要在任何你想要克隆的子类中将clone()方法重写为public的方法。
 * 例如，标准库中的{@link ArrayList}重写了clone()方法，这样就可以对ArrayList调用clone()方法了，
 * <p></p>
 * 本例演示了ArratList的clone()方法如何<b>不去</b> 自动尝试克隆ArrayList中的每个对象
 * 原有的ArrayList和克隆的ArrayList都是同一个对象的不同引用名.
 * 这是一种<b>浅拷贝</b>(shallow copy),因为之复制了对象的"表层部分"。
 * 实际对象的组成部分包括该"表层（引用）"、该引用指向的所有对象，以及所有<b>这些</b>对象所指向的所有对象，以此类推。
 * 这通常称为"对象网络"。创建所有这些内容的完整副本，称为<b>深拷贝</b>{@link com.kuifir.normal.reference.clone.deepCopy.AddingClone}
 *
 * @see com.kuifir.normal.reference.clone.deepCopy.AddingClone
 * @author kuifir
 * @date 2023/6/4 22:36
 */
public class CloneArrayList {
    public static void main(String[] args) {
        ArrayList<Int> v = IntStream.range(0, 10)
                .mapToObj(Int::new)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("v: " + v);
        @SuppressWarnings("unchecked")
        ArrayList<Int> v2 = (ArrayList<Int>) v.clone();
        // 对v2所有元素进行自增：
        v2.forEach(Int::increment);
        // 看看是否修改了v中元素
        // 你可以在输出中看到浅拷贝的效果，对v2执行的操作影响了v。
        // 不对ArrayList中的所有对象进行clone()可能是个合理的假设，因为无法保证这些对象都是可克隆的。
        System.out.println("v: " + v);
        System.out.println("v2: " + v2);
    }
}

class Int {
    private int i;

    public Int(int i) {
        this.i = i;
    }

    public void increment() {
        i++;
    }

    @Override
    public String toString() {
        return Integer.toString(i);
    }
}