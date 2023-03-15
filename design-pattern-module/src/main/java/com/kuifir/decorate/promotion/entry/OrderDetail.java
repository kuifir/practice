package com.kuifir.decorate.promotion.entry;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 详情订单
 */
@Data
public class OrderDetail {
    private int id; // 详细订单详情
    private int orderId; // 主订单id
    private Merchandise merchandise; // 商品详情
    private BigDecimal payMoney;// 支付订单

}
