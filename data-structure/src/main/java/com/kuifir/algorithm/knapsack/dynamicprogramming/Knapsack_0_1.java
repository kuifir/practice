package com.kuifir.algorithm.knapsack.dynamicprogramming;

import java.util.Arrays;

public class Knapsack_0_1 {
    public static void main(String[] args) {
        int[] items = {2, 5, 4, 2, 3};
        int[] values = {6, 3, 5, 4, 6};
        Knapsack_0_1 knapsack = new Knapsack_0_1(10, items, values);
        knapsack.getMaxValue();
    }


    /**
     * 总重量
     */
    private int maxWeight;
    /**
     * 物品数量
     */
    private int n;
    /**
     * 物品重量
     */
    private int weights[];
    /**
     * 物品价值
     */
    private int[] values;

    public Knapsack_0_1(int maxWeight, int[] weights, int[] values) {
        this.maxWeight = maxWeight;
        this.n = weights.length;
        this.weights = weights;
        this.values = values;
    }

    public void getMaxValue() {
        int[][] states = new int[n][maxWeight];
        programming(states);
        System.out.println(Arrays.stream(states)
                .flatMapToInt(Arrays::stream)
                .max().getAsInt());
        int[] states2 = new int[maxWeight];
        programming2(states2);
        System.out.println(Arrays.stream(states2).max().getAsInt());
    }

    void programming(int[][] states) {
        if (weights[0] < maxWeight) {
            states[0][weights[0]] = values[0];
        }
        // 第i层状态
        for (int i = 1; i < n; i++) {
            // 第i个商品不放入背包
            for (int j = 0; j < maxWeight; j++) {
                if (states[i - 1][j] > 0) {
                    states[i][j] = states[i - 1][j];
                }
            }
            // 第i个商品放入背包
            for (int j = 0; j < maxWeight - weights[i]; j++) {
                if ((states[i - 1][j] > 0)) {
                    if (states[i - 1][j] + values[i] > states[i][j + weights[i]]) {
                        states[i][j + weights[i]] = states[i - 1][j] + values[i];
                    }
                }
            }
        }
    }

    void programming2(int[] states) {
        if (weights[0] < maxWeight) {
            states[weights[0]] = values[0];
        }
        // 第i层状态
        for (int i = 1; i < n; i++) {
            // 第i个商品放入背包
            // 需要从大小到小遍历，否则会重复计算
            for (int j = maxWeight - weights[i] - 1; j >= 0; j--) {
                if (states[j] > 0) {
                    if (states[j] + values[i] > states[j + weights[i]]) {
                        states[j + weights[i]] = states[j] + values[i];
                    }
                }
            }
        }
        System.out.println(Arrays.toString(states));
    }
}
