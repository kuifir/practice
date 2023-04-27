package com.kuifir.normal.stream.optional;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 一些标准的流操作会返回Optional,因为他们不能确保所要的结果一定存在:
 * - findFirst()返回 包含第一个元素的 Optional。如果这个流为空，则返回Optional.empty。
 * - findAny()返回 包含任何元素的 Optional。如果这个流为空，则返回Optional.empty。
 * - max()和 min()分别返回 包含流中最大值或者最小值的 Optional。如果这个流为空，则返回Optional.empty。
 * - reduce()的一个版本，它并不以一个"identity"对象作为其第一个参数（在reduce其他版本中，"identity"对象会成为默认结果，所以不会有结果为空的风险），它会将返回值包在一个Optional中。
 * - 对于数值化的流IntStream,LongStream,DoubleStream,average()操作将其结果包在一个Optional中，以防为空的情况。
 */
public class OptionalsFromEmptyStreams {
    public static void main(String[] args) {
        System.out.println(Stream.<String>empty().findFirst());
        System.out.println(Stream.<String>empty().findAny());
        System.out.println(Stream.<String>empty().max(String.CASE_INSENSITIVE_ORDER));
        System.out.println(Stream.<String>empty().min(String.CASE_INSENSITIVE_ORDER));
        System.out.println(Stream.<String>empty().reduce((s1, s2) -> s1 + s2));
        System.out.println(IntStream.empty().average());
    }
}
