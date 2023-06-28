package com.kuifir.basic.constructorSafe;

/**
 * @author kuifir
 * @date 2023/6/28 23:18
 */
public class TestStaticIDField {
    public static void main(String[] args) {
        IDChecker.test(StaticIDField::new);
    }
}
