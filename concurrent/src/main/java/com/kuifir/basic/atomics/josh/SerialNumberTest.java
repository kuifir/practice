package com.kuifir.basic.atomics.josh;

/**
 * 测试基础版本的SerialNumbers类时，运行失败
 * volatile并未起到作用。要解决这个问题，就需要为nextSerialNumber()增加synchronized关键字。
 * @author kuifir
 * @date 2023/7/1 15:48
 */
public class SerialNumberTest {
    public static void main(String[] args) {
        SerialNumberChecker.test(new SerialNumbers());
    }
}
