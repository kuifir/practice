package com.kuifir.decorate.promotion.entry;

import lombok.Data;

/**
 * 促销类型
 */
@Data
public class SupportPromotions implements Cloneable{
    private int id; //该商品促销的id
    private PromotionType promotionType;//促销类型 1\优惠券 2\红包
    private int priority; //优先级
    private UserCoupon userCoupon;//用户领取该商品的优惠券
    private UserRedPacket userRedPacket; // 用户领取该商品的红包

    public SupportPromotions clone(){
        SupportPromotions supportPromotions = null;
        try {
            supportPromotions = (SupportPromotions) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return supportPromotions;
    }
}
