package com.kuifir.algorithm.sorting.swap;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] l = {49, 38, 65, 97, 76, 13, 27, 49, 22, 44};
        quickSort(l);
        System.out.println(Arrays.toString(l));
    }

    private static void quickSort(int[] l) {
        int low = 0;
        int high = l.length - 1;
        quickSort(l, 0, high);
    }

    private static void quickSort(int[] l, int low, int high) {
        if (low < high) {
            int pos = partition(l, low, high);
            quickSort(l, 0, pos - 1);
            quickSort(l, pos + 1, high);
        }
    }


    private static int partition(int[] l, int low, int high) {
        int tmp = l[low];
        while (low < high) {
            while (high > low && l[high] >= tmp) {
                high--;
            }
            l[low] = l[high];
            while (low < high && l[low] <= tmp) {
                low++;
            }
            l[high] = l[low];
        }
        l[low] = tmp;
        System.out.println(Arrays.toString(l));
        return low;
    }
}
