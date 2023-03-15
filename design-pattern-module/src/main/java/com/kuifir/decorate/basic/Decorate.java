package com.kuifir.decorate.basic;

/**
 * 装修基本类
 * @author kuifir
 */
public class Decorate implements IDecorator{

    /**
     * 基本实现方法
     */
    @Override
    public void decorator() {
        System.out.println("水电装修、天花板以及粉刷墙。。。");
    }
}
