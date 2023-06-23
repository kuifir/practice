package com.kuifir.parallelStream;

import java.util.Arrays;

/**
 * 最后换成装箱后的Long，再看看效果：
 * 现在可用的内存大概减少了一半，各处计算所需的时间都呈爆炸式增长，处理basicSum()，它只是简单地遍历了一遍数组。
 * 意外的是,Arrays.parallelPrefix()明显比其他方法都慢很多。
 * 还有parallel()的实现版本，会导致漫长的垃圾收集过程，所以单独拿出{@link Summing4}
 * 该方式比未使用parallel()的版本稍微快乐一点，但是差距不大。
 * <p></p>
 * 处理器的缓存机制是导致耗时增加的主要原因之一。
 * 由于{@link Summing2}中用的是基本类型long,
 * 因此数组la是一段连续的内存，处理器会更容易预测到对这个数组的使用情况，
 * 从而将数组元素保存在缓存中以备后续所需，而访问缓存远远比跳出去访问主存要快。
 * Long parallelPrefix()的计算看起来似乎受到了影响，因为它每次计算都要读取两个数组元素。
 * 还要将结果写回数组，每次这样的操作都会对Long生成一个缓存外的引用。
 * 在{@link Summing3} 和{@link Summing4}中，aL都是Long类型的数组，并不是一段连续的数值数组，
 * 而是一段连续的Long型对象引用的数组。尽管该数组很可能保存在缓存中，但其指向的那些对象几乎永远在缓存之外。
 * @author kuifir
 * @date 2023/6/23 23:23
 */
public class Summing3 {
    public static final int SZ = 1_00_000_000;
    public static final long CHECK = (long) SZ * ((long) SZ + 1) / 2;

    public static void main(String[] args) {
        System.out.println(CHECK);
        Long[] aL = new Long[SZ + 1];
        Arrays.parallelSetAll(aL, i -> (long) i);
        Summing.timeTest("Long Array Stream Reduce", CHECK, () -> Arrays.stream(aL).reduce(0L, Long::sum));
        Summing.timeTest("Long Basic Sum", CHECK, () -> basicSum(aL));
        Summing.timeTest("Long parallelPrefix", CHECK, () -> {
            Arrays.parallelPrefix(aL, Long::sum);
            return aL[aL.length - 1];
        });
        // 5000000050000000
        // Long Array Stream Reduce: 1987
        // Long Basic Sum: 263
        // Long parallelPrefix: 17850
    }

    static long basicSum(Long[] ia) {
        long sum = 0;
        int size = ia.length;
        for (int i = 0; i < size; i++) {
            sum += ia[i];
        }
        return sum;
    }
}
