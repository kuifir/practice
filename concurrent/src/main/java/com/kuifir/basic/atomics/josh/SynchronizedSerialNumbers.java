package com.kuifir.basic.atomics.josh;

/**
 * 不再需要volatile了，因为synchronized关键字确保实现了volatile的行为。
 * @author kuifir
 * @date 2023/7/1 15:51
 */
public class SynchronizedSerialNumbers extends SerialNumbers{
    private int serialNumber = 0;

    @Override
    public synchronized int nextSerialNumber() {
        return serialNumber++;
    }

    public static void main(String[] args) {
        SerialNumberChecker.test(new SynchronizedSerialNumbers());
    }
}
