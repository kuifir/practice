package com.kuifir.basic.parallelStream;

import java.util.Arrays;

/**
 * 现在试着实现需求：先给一个数组填充值，然后再对其求和。由于数组只分配了一次，看起来不大可能遇到垃圾收集的时机问题。
 * 首先试着将一个数组用基本类型long填充。
 * 第一个限制来自内存大小，由于数组是预先分配的，所以我们无法创建任何和之前版本大小相近的东西
 * 并行化可以提速，甚至比只是用basicSum()遍历还要快一点儿。
 * 有趣的是，Arrays.parallelPrefix()看起来反而会使程序慢下来。
 * 然而所有这些技术，在其它条件下可能会更适用——这就是为什么你无法预先确定该怎么做,而只能"试着来"。
 *
 * @author kuifir
 * @date 2023/6/23 23:03
 */
public class Summing2 {
    public static final int SZ = 20_000_000;
    public static final long CHECK = (long) SZ * ((long) SZ + 1) / 2;

    public static void main(String[] args) {
        System.out.println(CHECK);
        long[] la = new long[SZ + 1];
        Arrays.parallelSetAll(la, i -> i);
        Summing.timeTest("Array Stream Sum", CHECK, () -> Arrays.stream(la).sum());
        Summing.timeTest("Parallel", CHECK, () -> Arrays.stream(la).parallel().sum());
        Summing.timeTest("Basic Sum", CHECK, () -> basicSum(la));
        // 破坏性求和
        Summing.timeTest("ParallelPrefix", CHECK, () -> {
            Arrays.parallelPrefix(la, Long::sum);
            return la[la.length - 1];
        });
        // 200000010000000
        // Array Stream Sum: 30
        // Parallel: 17
        // Basic Sum: 24
        // ParallelPrefix: 108
    }

    static long basicSum(long[] ia) {
        long sum = 0;
        int size = ia.length;
        for (int i = 0; i < size; i++) {
            sum += ia[i];
        }
        return sum;
    }
}
