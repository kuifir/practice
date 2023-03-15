package com.kuifir.decorate.promotion.price;

import com.kuifir.decorate.promotion.entry.OrderDetail;

import java.math.BigDecimal;

/**
 * 计算支付金额的抽象类
 */
public abstract class BaseCountDecorator implements IBaseCount {

    private IBaseCount baseCount;

    public BaseCountDecorator(IBaseCount baseCount) {
        this.baseCount = baseCount;
    }

    @Override
    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        if (null != orderDetail) {
            return baseCount.countPayMoney(orderDetail);
        }
        return new BigDecimal(0);
    }
}
