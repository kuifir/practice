package com.kuifir.decorate.basic;

/**
 * 窗帘装饰类
 */
public class CurtainDecorator extends BaseDecorator{
    public CurtainDecorator(IDecorator decorator) {
        super(decorator);
    }
    /**
     * 窗帘具体装饰方法
     */
    @Override
    public void decorator(){
        super.decorator();
        System.out.println("窗帘装饰。。。");
    }
}
