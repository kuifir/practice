package com.kuifir.normal.generic.selfBounded;

/**
 * 这是一个很普通的泛型类型，它内部包含两个方法，
 * 分别用于接收和生成类型与参数类型一致的对象，以及一个用于操作存储的字段的方法。（虽然只对该字段执行了Object的操作）
 * 可以将BasicHolder用于CRG(奇异递归泛型)中：
 * {@link CRGWithBasicHolder}
 * <p></p>
 * BasicHolder 可以将任何类型作为其泛型参数，如{@link Unconstrained} 所示.
 *
 * @see SelfBounded
 * @see CRGWithBasicHolder
 * @author kuifir
 * @date 2023/5/28 15:11
 */
public class BasicHolder<T> {
    T element;
    void set(T arg){
        element = arg;
    }
    T get(){
        return element;
    }
    void f(){
        System.out.println(element.getClass().getSimpleName());;
    }
}
