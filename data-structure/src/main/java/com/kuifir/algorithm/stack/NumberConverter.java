package com.kuifir.algorithm.stack;

import com.kuifir.table.stack.LinkStack;

/**
 * 整数进制转换
 */
public class NumberConverter {
    public static void main(String[] args) {
        System.out.println(numberConvert(2, 1348));
    }

    /**
     * 将非负数十进制整数转换为m进制的数字
     *
     * @param m
     * @param target
     * @return
     */
    public static String numberConvert(Integer m, int target) {
        LinkStack<Integer> stack = new LinkStack<>();
        int div = target;
        int mod = 0;
        while (div > 0) {
            mod = div % m;
            div = div / m;
            stack.push(mod);
        }
        System.out.println(stack);
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()){
            Integer pop = stack.pop();
            result.append(pop);
        }
        return result.toString();
    }
}
