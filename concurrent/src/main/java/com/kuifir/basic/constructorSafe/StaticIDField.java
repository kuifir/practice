package com.kuifir.basic.constructorSafe;

/**
 * 测试类：{@link TestStaticIDField}
 * @author kuifir
 * @date 2023/6/28 23:01
 */
public class StaticIDField implements HasID {
    private static int counter = 0;
    private int id = counter++;

    @Override
    public int getID() {
        return id;
    }
}
