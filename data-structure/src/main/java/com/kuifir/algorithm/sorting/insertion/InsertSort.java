package com.kuifir.algorithm.sorting.insertion;

import java.util.Arrays;

/**
 * 直接插入排序
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] a = {2, 1, 7, 8, 4, 5, 6, 9, 3};
//        System.out.println(Arrays.toString(insertSort(a)));
//        System.out.println(Arrays.toString(insertAfterSort(a)));
        System.out.println(Arrays.toString(binaryInsertSort(a)));
    }

    public static int[] insertSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                int j = 0;
                int tmp = a[i];
                // 找插入位置
                while ((j < i - 1) && (a[j] <= a[i])) {
                    j++;
                }
                // 移动
                for (int k = i; k > j; k--) {
                    a[k] = a[k - 1];
                }
                a[j] = tmp;
            }
        }
        return a;
    }

    public static int[] insertAfterSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                int tmp = a[i];
                int j = i - 1;
                while (j >= 0 && a[j] > tmp) {
                    a[j + 1] = a[j];
                    j--;
                }
                a[j + 1] = tmp;
            }
        }
        return a;
    }

    /**
     * 折半插入排序
     * @param a
     * @return
     */
    public static int[] binaryInsertSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                int tmp = a[i];
                int low = 0;
                int high = i - 1;
                while (low <= high) {
                    int m = (low + high) / 2;
                    if (a[m] > tmp) {
                        high = m - 1;
                    } else {
                        low = m + 1;
                    }
                }
                for (int j = i - 1; j >= low; j--) {
                    a[j + 1] = a[j];
                }
                a[low] = tmp;
            }
        }
        return a;
    }
}
