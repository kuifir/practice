package com.kuifir.invoke.method.identify;

import java.util.Random;

/**
 * 奸商
 * <p>
 * 具体来说，Java 字节码中与调用相关的指令共有五种。
 * - invokestatic：用于调用静态方法。
 * - invokespecial：用于调用私有实例方法、构造器，以及使用 super 关键字调用父类的实例方法或构造器，和所实现接口的默认方法。
 * - invokevirtual：用于调用非私有实例方法。
 * - invokeinterface：用于调用接口方法。
 * - invokedynamic：用于调用动态方法
 *
 * @author kuifir
 * @date 2023/11/4 17:33
 */
public class UnscrupulousMerchant extends Merchant {
    //    @Override
//    public double actionPrice(double price, Custom custom) {
//        if (custom.isVIP()) {  // invokeinterface
//            return price * discrimination();  // invokestatic
//        }
//        return super.actionPrice(price, custom); // invokespecial
//    }
    @Override
    public Double actionPrice(double price, Custom custom) {
        return price * discrimination();
    }

    public static double discrimination() {
        // 咱们的杀熟算法太粗暴了，应该将客户城市作为随机数生成器的种子。
        return new Random()   // invokespecial
                .nextDouble()  // invokevirtual
                + 0.8d;
    }
}
