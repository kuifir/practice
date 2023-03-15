package com.kuifir.decorate.promotion.price;

import com.kuifir.decorate.promotion.entry.OrderDetail;
import com.kuifir.decorate.promotion.entry.PromotionType;
import com.kuifir.decorate.promotion.entry.SupportPromotions;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 计算促销后的支付价格
 */
public class PromotionFactory {
    public static BigDecimal getPayMoney(OrderDetail orderDetail){
        // 获取给商品设定的促销类型
        Map<PromotionType, SupportPromotions> supportPromotions = orderDetail.getMerchandise().getSupportPromotions();
        //初始化计算类
        IBaseCount baseCount = new BaseCount();
        if(null != supportPromotions && supportPromotions.size()>0){
            for (PromotionType promotionType : supportPromotions.keySet()){
                baseCount = promotion(supportPromotions.get(promotionType),baseCount);
            }
        }
        return baseCount.countPayMoney(orderDetail);
    }

    /**
     * 组合促销类型
     * @param supportPromotions
     * @param baseCount
     * @return
     */
    private static IBaseCount promotion(SupportPromotions supportPromotions, IBaseCount baseCount) {
        if(supportPromotions.getPromotionType() == PromotionType.COUPON){
            baseCount = new CouponDecorator(baseCount);
        }else if (supportPromotions.getPromotionType() == PromotionType.REDPACKED){
            baseCount = new RedPacketDecorator(baseCount);
        }
        return baseCount;
    }
}
