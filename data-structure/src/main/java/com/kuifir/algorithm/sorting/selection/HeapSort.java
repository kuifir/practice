package com.kuifir.algorithm.sorting.selection;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] l = {49, 38, 65, 97, 76, 13, 27, 49};
        heapSort(l);
        System.out.println(Arrays.toString(l));
    }

    public static void heapSort(int[] l) {
        // 构造大顶堆
        for (int i = l.length / 2; i > 0; i--) {
            heapAdjust2(l, i, l.length);
        }
        System.out.println(Arrays.toString(l));
        // 筛选下一个最大值
        for (int i = 1; i < l.length; i++) {
            int tmp = l[0];
            l[0] = l[l.length - i];
            l[l.length - i] = tmp;
            heapAdjust2(l, 1, l.length - i);
            System.out.println(Arrays.toString(l));
        }
    }

    // m+1 至 n 符合大顶堆，将m 至n 调整到最大堆
    public static void heapAdjust(int[] l, int m, int n) {
        for (int j = m * 2; j <= n; j = 2 * j) {
            int max = ((j < n) && (l[j - 1] < l[j])) ? j : j - 1;
            if (l[j / 2 - 1] < l[max]) {
                int tmp = l[j / 2 - 1];
                l[j / 2 - 1] = l[max];
                l[max] = tmp;
            } else {
                break;
            }
        }
    }

    // m+1 至 n 符合大顶堆，将m 至n 调整到最大堆
    public static void heapAdjust2(int[] l, int m, int n) {
        while (2 * m <= n) {
            int left = 2 * m;
            int right = 2 * m + 1;
            int max = (left < n) && (l[right - 1] > l[left - 1]) ? right : left;
            if (l[m - 1] < l[max - 1]) {
                int tmp = l[m - 1];
                l[m - 1] = l[max - 1];
                l[max - 1] = tmp;
                m = max;
            } else {
                break;
            }
        }
    }
}
