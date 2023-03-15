package com.kuifir.decorate.promotion.price;

import com.kuifir.decorate.promotion.entry.OrderDetail;

import java.math.BigDecimal;

/**
 * 计算支付金额接口
 */
public interface IBaseCount {
    BigDecimal countPayMoney(OrderDetail orderDetail);
}
