package com.kuifir.invoke.method;

/**
 * 重载的方法在编译过程中即可完成识别。
 * 具体到每一个方法调用，Java 编译器会根据所传入参数的声明类型（注意与实际类型区分）来选取重载方法。
 * 选取的过程共分为三个阶段：
 * - 在不考虑对基本类型自动装拆箱（auto-boxing，auto-unboxing），以及可变长参数的情况下选取重载方法；
 * - 如果在第 1 个阶段中没有找到适配的方法，那么在允许自动装拆箱，但不允许可变长参数的情况下选取重载方法；
 * - 如果在第 2 个阶段中没有找到适配的方法，那么在允许自动装拆箱以及可变长参数的情况下选取重载方法。
 *
 * <p>
 *     如果 Java 编译器在同一个阶段中找到了多个适配的方法，那么它会在其中选择一个最为贴切的，而决定贴切程度的一个关键就是形式参数类型的继承关系。
 *     当传入 null 时，它既可以匹配第一个方法中声明为 Object 的形式参数，也可以匹配第二个方法中声明为 String 的形式参数。
 *     由于 String 是 Object 的子类，因此 Java 编译器会认为第二个方法更为贴切。
 * </p>
 * @author kuifir
 * @date 2023/11/4 17:06
 */
public class InvokeOrderd {
    void invoke(Object obj, Object... args) {
        System.out.println(1);
    }

    void invoke(String s, Object obj, Object... args) {
        System.out.println(2);
    }

    public static void main(String[] args) {
        InvokeOrderd invokeOrderd = new InvokeOrderd();
        // 调用第二个invoke方法
        invokeOrderd.invoke(null, 1);
        // 调用第二个invoke方法
        invokeOrderd.invoke(null, 1, 2);
        // 只有手动绕开可变长参数的语法糖，
        // 才能调用第一个invoke方法
        invokeOrderd.invoke(null, new Object[]{1});
    }

}
