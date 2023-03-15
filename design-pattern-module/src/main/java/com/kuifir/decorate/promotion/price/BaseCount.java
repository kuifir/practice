package com.kuifir.decorate.promotion.price;

import com.kuifir.decorate.promotion.entry.OrderDetail;

import java.math.BigDecimal;

/**
 * 支付基本类
 */
public class BaseCount implements IBaseCount {
    @Override
    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        orderDetail.setPayMoney(orderDetail.getMerchandise().getPrice());
        System.out.println("商品原单价金额为：" + orderDetail.getPayMoney());
        return orderDetail.getPayMoney();
    }
}
