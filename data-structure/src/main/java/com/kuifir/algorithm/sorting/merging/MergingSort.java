package com.kuifir.algorithm.sorting.merging;

import java.util.Arrays;

public class MergingSort {
    public static void main(String[] args) {
        int[] l = {49, 38, 65, 97, 76, 13, 27};
        mergingSort(l, 0, l.length-1);
        System.out.println(Arrays.toString(l));
    }

    public static void mergingSort(int[] l, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergingSort(l, low, mid);
            mergingSort(l, mid + 1, high);
            merge(l, low, mid, high);
        }

    }

    /**
     * 有序表的有序归并
     *
     * @param
     * @return
     */
    public static void merge(int[] l, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];
        // 右表起始下标
        int i = mid + 1;
        int j = 0;
        while (left <= mid && i <= right) {
            if (l[left] <= l[i]) {
                tmp[j++] = l[left++];
            } else {
                tmp[j++] = l[i++];
            }
        }
        while (left <= mid) {
            tmp[j++] = l[left++];
        }

        while (i <= right) {
            tmp[j++] = l[i++];
        }
        while (j > 0) {
            l[right--] = tmp[--j];
        }
    }
}
