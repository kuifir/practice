package com.kuifir.basic.threads;

/**
 * 检测当前机器的处理器数量
 * @author kuifir
 * @date 2023/6/29 21:58
 */
public class NumberOfProcessors {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
