package com.kuifir.decorate.promotion.entry;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 红包
 */
@Data
public class UserRedPacket {
    private int id; //优惠券id
    private int userId; //领取优惠券用户ID
    private String sku; // 商品SKU
    private BigDecimal redPacket; //领取红包金额
}
