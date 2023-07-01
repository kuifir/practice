package com.kuifir.basic.atomics.josh;

import java.util.Arrays;

/**
 * add()和contains()方法是synchronized的，以防止线程冲突。
 * @author kuifir
 * @date 2023/7/1 15:36
 */
public class CircularSet {
    private int[] array;
    private int size;
    private int index = 0;

    public CircularSet(int size) {
        this.size = size;
        array = new int[size];
        // 初始化为非SerialNumbers生成的值
        Arrays.fill(array, -1);
    }

    public synchronized void add(int i) {
        array[index] = i;
        // 包装索引，并复写旧的元素
        index = ++index % size;
    }

    public synchronized boolean contains(int val) {
        for (int i = 0; i < size; i++) {
            if (array[i] == val) {
                return true;
            }
        }
        return false;
    }
}
