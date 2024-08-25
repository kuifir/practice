package com.kuifir.algorithm.sorting.swap;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] l = {49, 38, 65, 97, 76, 13, 27, 49};
        bubbleSort(l);
        System.out.println(Arrays.toString(l));
    }

    public static void bubbleSort(int[] l) {
        for (int i = 1; i < l.length; i++) {
            boolean swapFlag = false;
            for (int j = 0; j < l.length - i; j++) {
                if (l[j] > l[j + 1]) {
                    int tmp = l[j];
                    l[j] = l[j + 1];
                    l[j + 1] = tmp;
                    swapFlag = true;
                }
            }
            if (!swapFlag) {
                break;
            }
            System.out.println(Arrays.toString(l));
        }
    }
}
