package com.kuifir.normal.generic.selfBounded;

/**
 * 将BasicHolder用于CRG中
 * 此处要注意一个重点：SubType这个新类接收参数和返回值的类型都是SubType，而不是积累BasicHolder.
 * 这便是CRG的精髓了：基类用子类替换了其参数。
 * 这意味着泛型基类变成了一种为其子类实现通用功能的模板，但是所实现的功能会将派生类型用于所有的参数和返回值。
 * 也就是说，最终类使用的类型是具体的类型，而不是基类、因此在Subtype中，set()的参数和get()的返回值类型均为确切的SubType。
 * @see BasicHolder
 * @author kuifir
 * @date 2023/5/28 15:58
 */
public class CRGWithBasicHolder {
    public static void main(String[] args) {
        SubType st1 = new SubType();
        SubType st2 = new SubType();
        st1.set(st2);
        SubType st3 = st1.get();
        st1.f();
    }
}
class SubType extends BasicHolder<SubType>{

}