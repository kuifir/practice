package com.kuifir.algorithm.stack;

public class Hanoi {
    public static void main(String[] args) {
        hanoi(5,"a","b","c");
    }

    public static void hanoi(int n, String a, String b, String c) {
        if (n == 1) {
            System.out.println(n + "号盘 --》 从  " + a + "号柱 到  " + c + "号柱子 ");
            return;
        }
        hanoi(n - 1, a, c, b);
        System.out.println(n + "号盘 --》 从  " + a + "号柱 到  " + c + "号柱子 ");
        hanoi(n-1,b,a,c);
    }
}
