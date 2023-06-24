package com.kuifir.basic.parallelStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * current由线程安全的AtomicInteger类来定义，以避免竞态条件的发生，parallel()则允许多线程来调用get()方法。
 * 当你看待PSP2.txt时可能会感到吃惊(使用Java8),IntGenerator.get()竟被调用了成百上千次。(Java17不会，但是还是比limit的数要大%20左右)
 * 这些分块的大小似乎是有内部实现决定的（是是想limit()传入不同的参数，可以看到不同的分块大小）。
 * 将parallel()和limit()一起配合使用，可以告诉程序预先选取一组值，以作为流输出。
 * <p></p>
 * 试着想象一下这里面究竟发生了什么：流抽象了一个可按需生产的无限序列。
 * 当你让它以并行方式生成流时，实际上实在让所有的线程都尽可能调用get()方法。
 * 加上limit()意味着你想要的是"只需要一些"。基本上，如果你同时使用parallel()和limit(),
 * 那就是在请求随机的输出——对当前要解决的需求来说，这可能没什么问题，但你这么做的时候，必须要清楚这一点。
 * 此功能只适合高手使用，并不能拿来作为证明"Java运行有问题"的理由。
 * <p></p>
 * 对该问题来说，增氧的实现方式更合理呢？
 * 如果你想生成int流，可以使用IntStream.range()就像{@link ParallelStreamPuzzle3}
 * @author kuifir
 * @date 2023/6/24 0:26
 * @see ParallelStreamPuzzle
 */
public class ParallelStreamPuzzle2 {
    public static final Deque<String> trace = new ConcurrentLinkedDeque<>();

    static class IntGenerator implements Supplier<Integer> {
        private AtomicInteger current = new AtomicInteger();

        @Override
        public Integer get() {
            trace.add(current.get() + ":" + Thread.currentThread().getName());
            return current.getAndIncrement();
        }
    }

    public static void main(String[] args) throws IOException {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(1000)
                .parallel()
                .collect(Collectors.toList());
        // [1, 4, 6, 9, 0, 2, 3, 5, 7, 8]
        System.out.println(x);
        Files.write(Paths.get("PSP2.txt"), trace);
    }
}
