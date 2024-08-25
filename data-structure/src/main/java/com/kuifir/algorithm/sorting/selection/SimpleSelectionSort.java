package com.kuifir.algorithm.sorting.selection;

import java.util.Arrays;

/**
 * 简单选择排序
 */
public class SimpleSelectionSort {
    public static void main(String[] args) {
        int[] l = {49, 38, 65, 97, 49, 13, 27, 76};
        simpleSelectionSort(l);
        System.out.println(Arrays.toString(l));

    }

    public static void simpleSelectionSort(int[] l) {
        for (int i = 0; i < l.length - 1; i++) {
            int min = i;
            for (int j = l.length - 1; j > i; j--) {
                if (l[j] < l[min]) {
                    min = j;
                }
            }
            if(min !=i){
                int tmp = l[i];
                l[i] = l[min];
                l[min] = tmp;
            }

            System.out.println(Arrays.toString(l));
        }

    }
}
