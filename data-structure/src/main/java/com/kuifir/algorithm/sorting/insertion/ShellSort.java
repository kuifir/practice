package com.kuifir.algorithm.sorting.insertion;

import java.util.Arrays;
import java.util.stream.Stream;

public class ShellSort {
    public static void main(String[] args) {
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49, 55, 4};
        Stream.of(5, 3, 1).forEach(i -> {
            shellSort(a, i);
            System.out.println(Arrays.toString(a));
        });
    }

    public static void shellSort(int[] l, int dk) {
        for (int i = dk; i < l.length; i++) {
            if (l[i] < l[i - dk]) {
                int tmp = l[i];
                int j = i - dk;
                // 查位置，做移动
                for (; (j >= 0) && l[j] > tmp; j -= dk) {
                    l[j + dk] = l[j];
                }
                l[j + dk] = tmp;
            }
        }
    }
}
