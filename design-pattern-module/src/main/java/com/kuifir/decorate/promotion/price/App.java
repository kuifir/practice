package com.kuifir.decorate.promotion.price;

import com.kuifir.decorate.promotion.entry.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Order order = new Order();
        init(order);
        for(OrderDetail orderDetail: order.getList()) {
            BigDecimal payMoney = PromotionFactory.getPayMoney(orderDetail);
            orderDetail.setPayMoney(payMoney);
            System.out.println("最终支付金额：" + orderDetail.getPayMoney());
        }
    }

    private static void init(Order order) {
        Map<PromotionType, SupportPromotions> supportPromotionslist = new HashMap<PromotionType, SupportPromotions>();

        SupportPromotions supportPromotions = new SupportPromotions();
        supportPromotions.setPromotionType(PromotionType.COUPON);
        supportPromotions.setPriority(1);

        UserCoupon userCoupon= new UserCoupon();
        userCoupon.setCoupon(new BigDecimal(3));
        userCoupon.setSku("aaa1111");
        userCoupon.setUserId(11);

        supportPromotions.setUserCoupon(userCoupon);

        supportPromotionslist.put(PromotionType.COUPON, supportPromotions);
        SupportPromotions supportPromotions1 = supportPromotions.clone();

        supportPromotions1.setPromotionType(PromotionType.REDPACKED);
        supportPromotions1.setPriority(2);

        UserRedPacket userRedPacket= new UserRedPacket();
        userRedPacket.setId(1);
        userRedPacket.setRedPacket(new BigDecimal(10));
        userRedPacket.setSku("aaa1111");
        userCoupon.setUserId(11);

        supportPromotions1.setUserRedPacket(userRedPacket);
        supportPromotionslist.put(PromotionType.REDPACKED, supportPromotions1);

        Merchandise merchandise = new Merchandise();
        merchandise.setSku("aaa1111");
        merchandise.setName("苹果");
        merchandise.setPrice(new BigDecimal(20));
        merchandise.setSupportPromotions(supportPromotionslist);

        List<OrderDetail> OrderDetailList = new ArrayList<OrderDetail>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1);
        orderDetail.setOrderId(1111);
        orderDetail.setMerchandise(merchandise);

        OrderDetailList.add(orderDetail);

        order.setList(OrderDetailList);

    }
}
