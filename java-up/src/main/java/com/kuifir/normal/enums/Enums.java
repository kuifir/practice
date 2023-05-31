package com.kuifir.normal.enums;

import java.util.Random;

/**
 * 工具类
 * @author kuifir
 * @date 2023/5/31 23:01
 */
public class Enums {
    /**
     * 从枚举中随机选择实例
     * {@link Class#getEnumConstants()}
     */
    private static final Random RAND = new Random();
    public static <T extends Enum<T>> T random(Class<T> ec){
        return random(ec.getEnumConstants());
    }
    public static <T> T random(T[] values){
        return values[RAND.nextInt(values.length)];
    }
}
