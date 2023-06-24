package com.kuifir.basic.parallelStream;

import cn.hutool.core.date.StopWatch;

import java.util.function.LongSupplier;
import java.util.stream.LongStream;

/**
 * parallel()并非"灵丹妙药"
 * 为了探寻流和并行流的不确定性，我们来看一个貌似很简单的问题：对一系列递增的数字求和。
 * 首先实现一个timeTest()方法,他接受LongSupplier为参数，
 * 并测量调用getAsLong()的执行耗时，然后和checkValue作比较，最后显示结果。
 * 注意，这里必须全部严格使用long类型，我开始花了些时间追查那些隐蔽的溢出问题，后来才意识到是忽略了使用long类型的重要性。
 * <p></p>
 * 使用parallel()是相当合理的想法，除却内存限制的原因{@link },
 * 可以先对流并行算法做一些初步的观察总结
 * <ul>
 *     <li>流的并行化将输入的数据拆分成多个片段，这样就可以针对这些独立的数据片段应用各种算法</li>
 *     <li>数组的切分非常轻量、均匀，并且可以完全掌握切分的大小</li>
 *     <li>链表则完全没有这些属性，对链表"切分"仅仅意味着会将其拆分成"第一个元素"和"其余的部分"，这并没有什么实际用处。</li>
 *     <li>无状态生成器的表现很像数组，以上对range的使用就是无状态的。</li>
 *     <li>迭代式生成器的表现很像链表，iterate()就是一个迭代式生成器。</li>
 * </ul>
 * @author kuifir
 * @date 2023/6/23 22:27
 */
public class Summing {
    public static final int SZ = 1_00_000_000;
    public static final long CHECK = (long) SZ * ((long) SZ + 1) / 2;

    public static void main(String[] args) {
        System.out.println(CHECK);
        timeTest("Sum Stream", CHECK, () -> LongStream.rangeClosed(0, SZ).sum());
        timeTest("Sum Stream Parallel", CHECK, () -> LongStream.rangeClosed(0, SZ).parallel().sum());
        timeTest("Sum Iterated", CHECK, () -> LongStream.iterate(0, i -> i + 1).limit(SZ + 1).sum());
        // 超过议定书之后会开始变慢或者内存溢出：例如到1_000_000_000时会内存溢出
        timeTest("Sum Iterated Parallel", CHECK, () -> LongStream.iterate(0, i -> i + 1).parallel().limit(SZ + 1).sum());
        // 5000000050000000
        // Sum Stream: 171
        // Sum Stream Parallel: 25
        // Sum Iterated: 260
        // Sum Iterated Parallel: 2430
    }

    static void timeTest(String id, long checkValue, LongSupplier operation) {
        System.out.print(id + ": ");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long result = operation.getAsLong();
        stopWatch.stop();
        if (result == checkValue) {
            System.out.println(stopWatch.getLastTaskTimeMillis());
        } else {
            System.out.format("result: %d%n checkValue: %d%n", result, checkValue);
        }
    }
}
