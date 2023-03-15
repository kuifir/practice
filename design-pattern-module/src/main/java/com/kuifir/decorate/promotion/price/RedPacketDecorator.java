package com.kuifir.decorate.promotion.price;

import com.kuifir.decorate.promotion.entry.OrderDetail;
import com.kuifir.decorate.promotion.entry.PromotionType;

import java.math.BigDecimal;

public class RedPacketDecorator extends BaseCountDecorator{
    public RedPacketDecorator(IBaseCount baseCount) {
        super(baseCount);
    }

    @Override
    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        super.countPayMoney(orderDetail);
        return redPackPayMoney(orderDetail);
    }

    public BigDecimal redPackPayMoney(OrderDetail orderDetail){
        BigDecimal redPacket = orderDetail.getMerchandise().getSupportPromotions().get(PromotionType.REDPACKED).getUserRedPacket().getRedPacket();
        System.out.println("商品红包金额: "+redPacket);
        orderDetail.setPayMoney(orderDetail.getPayMoney().subtract(redPacket));
        return orderDetail.getPayMoney();
    }
}
