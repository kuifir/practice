package com.kuifir.normal.enums;

/**
 * 如果向上转型枚举，便会丢失values()方法.
 * 然而要注意的是，Class中有个getEnumConstants()方法，
 * 所以即使Enum的接口中没有values()方法，仍可以通过Class对象来的带enum的实例
 * <p></p>
 * 由于getEnumConstants()是Class类中的一个方法，因此对一个没有枚举的类也可以调用该方法。
 *
 * @author kuifir
 * @date 2023/5/31 0:02
 */
public class UpcastEnum {
    public static void main(String[] args) {
        Search[] values = Search.values();
        // 向上转型
        Enum hither = Search.HITHER;

        // hither.values(); Enum中没有values()方法
        for (Enum enumConstant : hither.getClass().getEnumConstants()) {
            System.out.println(enumConstant);
        }
        // 由于getEnumConstants()是Class类中的一个方法，因此对一个没有枚举的类也可以调用该方法。
        Class<Integer> integerClass = Integer.class;
        try {
            for (Integer enumConstant : integerClass.getEnumConstants()) {
                System.out.println(enumConstant);
            }
        }catch (Exception e){
            System.out.println("Expected:" + e);
        }
    }
}

enum Search {HITHER, YON}
