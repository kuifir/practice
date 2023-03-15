package com.kuifir.decorate.basic;


/**
 *
 * 装饰器模式包括了以下几个角色：接口、具体对象、装饰类、具体装饰类。
 * 接口定义了具体对象的一些实现方法； {@link IDecorator}
 * 具体对象定义了一些初始化操作；{@link Decorate}
 * 装饰类则是一个抽象类，主要用来初始化具体对象的一个类；{@link BaseDecorator}
 * 其它的具体装饰类都继承了该抽象类
 * <p>
 * 定义一个基本装修接口
 * @author kuifir
 */
public interface IDecorator {

    /**
     * 装修方法
     */
    void decorator();

}
