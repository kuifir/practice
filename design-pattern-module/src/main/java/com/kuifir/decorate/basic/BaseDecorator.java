package com.kuifir.decorate.basic;

/**
 * 基本装饰类
 */
public abstract class BaseDecorator implements IDecorator {
    private IDecorator decorator;

    public BaseDecorator(IDecorator decorator) {
        this.decorator = decorator;
    }

    /**
     * 调用装饰方法
     */
    @Override
    public void decorator() {
        if(null != decorator){
            decorator.decorator();
        }
    }
}
