package com.kuifir.parallelStream;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * parallel()和 limit()的作用
 * parallel()还能带来进一步的影响。
 * 和其它语言一样，流是围绕无限流的模型设计的。
 * - 如果要处理有限数量的元素，就需要使用集合，以及专门为有限大小的集合所设计的相关算法。
 * - 如果使用无限流，则需要使用这些算法专门为流优化后的版本。
 * Java8合并了以上两种情况。举例来说，Collection没有内建的map()操作，Collection和Map中唯一的流式批处理操作是forEach().
 * 如果你想要执行类似map()和reduce()的操作，就需要将Collection转换为Stream,这样才能有这些操作可用。
 * <p></p>
 * 在许多场景中，单纯地调用stream()或parallelStream()是没有任何问题的。然而有时，Stream和Collection同时出现则会造成意外：
 * 如果使用<pre>{@code
 *  List<Integer> x = Stream.generate(new IntGenerator())
 *                 .limit(1000)
 *                 .collect(Collectors.toList());
 *         System.out.println(x);
 * }</pre>,则每次都会如与其那样得到:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
 * 但是如果使用<pre>{@code
 *  List<Integer> x = Stream.generate(new IntGenerator())
 *                 .limit(1000)
 *                 .parallel()
 *                 .collect(Collectors.toList());
 *         System.out.println(x);}</pre>，使用了parallel()，
 *         则程序似乎变成了一个随机数生成器，输出每次都不一样:[4, 7, 9, 0, 2, 3, 5, 6, 8, 1]。（Java8更明显）
 * <p></p>
 * 为何如此简单的程序也不稳定？来想想此刻的目的"并行生成"。这意味着什么呢？
 * 一堆线程全都运行在一个生成器上，然后以某种方式选择一组有限的结果？
 * 代码看起来很简单，但最终造成了特别混乱的状况。
 * 为了研究这个问题，需要添加一些工具。我们处理的是多线程，因此必须捕获所有的追踪信息，并保存到并发数据结构中。
 * 此处用到了ConcurrentLinkedDeque。{@link ParallelStreamPuzzle2}
 *
 * @author kuifir
 * @date 2023/6/23 23:57
 */
public class ParallelStreamPuzzle {
    static class IntGenerator implements Supplier<Integer> {
        private int current = 0;

        @Override
        public Integer get() {
            return current++;
        }
    }

    public static void main(String[] args) {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(10)
                .parallel()
                .collect(Collectors.toList());
        System.out.println(x);
    }
}
