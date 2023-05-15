package com.kuifir.normal.interfaces;

/**
 * 当实现的两个接口有相同方法签名的话，回发生编译时错误(inherits unrelated defaults for jim() from types)。
 * 要解决这个问题，必须重写冲突方法。
 * 通常会用super关键字来选择一个基类实现。
 * @author kuifir
 * @date 2023/5/15 22:12
 */
public class Jim implements Jim1,Jim2{

    /**
     * 可以将jim()重新定义为任何东西
     * 通常会用super关键字来选择一个基类实现
     */
    @Override
    public void jim() {
        Jim1.super.jim();
    }
}
interface Jim1{
    default void jim(){
        System.out.println("Jim1::jim");
    };
}
interface Jim2{
    default void jim(){
        System.out.println("Jim2::jim");
    }
}