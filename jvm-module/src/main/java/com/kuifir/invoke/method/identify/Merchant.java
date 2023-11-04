package com.kuifir.invoke.method.identify;

/**
 * 商户
 *
 * @author kuifir
 * @date 2023/11/4 17:32
 */
public class Merchant {
    /**
     * 折后价格
     *
     * @param price  价格
     * @param custom 客户
     * @return
     */
//    public double actionPrice(double price, Custom custom) {
//        return price * 0.8d;
//    }
    public Number actionPrice(double price, Custom custom) {
        return price * 0.8d;
    }
}
