package com.kuifir.volatiles;

/**
 * 试着写一个小的程序, 看看使用 volatile 关键字和不使用 volatile 关键字，
 * 在数据写入的性能上会不会有差异，以及这个差异到底会有多大。
 *
 * @author kuifir
 * @date 2023/6/5 22:33
 */
public class VolatileTest4 {
    private static volatile int NUM_VOLATILE = 1;
    private static int NUM = 1;

    public static void main(String[] args) {
        int[] arr = new int[80000000];
        long l = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = NUM_VOLATILE++;
        }
        // 638
        System.out.println(System.currentTimeMillis() - l);
        l = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = NUM++;
        }
        // 79
        System.out.println(System.currentTimeMillis() - l);
    }
}
