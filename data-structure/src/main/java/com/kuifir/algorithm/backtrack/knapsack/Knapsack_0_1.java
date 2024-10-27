package com.kuifir.algorithm.backtrack.knapsack;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 有一个背包，背包总的承载重量是 Wkg。
 * 现在我们有 n 个物品，每个物品的重量不等，并且不可分割。
 * 我们现在期望选择几件物品，装载到背包中。在不超过背包所能装载重量的前提下，
 * 如何让背包中物品的总价值最大？
 */
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

    /**
     * 对于每个物品来说，都有两种选择，装进背包或者不装进背包。
     * 对于 n 个物品来说，总的装法就有 2^n 种，去掉总重量超过 Wkg 的，
     * 从剩下的装法中选择总重量最接近 Wkg 的。
     */
    public void getMaxValue() {
        // 背包当前重量
        AtomicInteger weight = new AtomicInteger(0);
        // 背包物品
        int[] items = new int[n];
        // 背包价值
        AtomicInteger value = new AtomicInteger(0);
        AtomicInteger maxValue = new AtomicInteger(0);
        fillBack(0, weight, items, value, maxValue);
        System.out.println("最大价值：" + maxValue.get());
    }

    /**
     * 对第i个物品做处理
     *
     * @param i      第i个物品
     * @param weight
     */
    void fillBack(int i, AtomicInteger weight, int[] items, AtomicInteger value, AtomicInteger maxValue) {
        if (this.n == i || weight.get() == maxWeight) {
            if (value.get() > maxValue.get()) {
                maxValue.set(value.get());
            }
            System.out.println("物品：" + Arrays.toString(items) + ",重量：" + weight.get() + ",价值：" + value.get());
            return;
        }
        // 如果不装第i个物品
        int w = 0;
        int v = 0;
        for (int j = 0; j < n; j++) {
            if (j < i) {
                if (items[j] > 0) {
                    w += weights[j];
                    v += values[j];
                }
            } else {
                items[j] = 0;
            }
        }
        weight = new AtomicInteger(w);
        value = new AtomicInteger(v);
        fillBack(i + 1, weight, items, value, maxValue);
        // 如果装第i个物品
        items[i] = 1;
        weight = new AtomicInteger(w);
        value = new AtomicInteger(v);
        int weightAndAdd = weight.addAndGet(weights[i]);
        value.addAndGet(values[i]);
        if (weightAndAdd <= maxWeight) {
            fillBack(i + 1, weight, items, value, maxValue);
        }
    }
}
