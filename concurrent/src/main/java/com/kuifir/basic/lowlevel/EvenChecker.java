package com.kuifir.basic.lowlevel;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 *     test()方法启动了多个访问同一个{@link IntGenerator}的{@link EvenChecker}.
 * EvenChecker任务持续从各自的关联IntGenerator中读取值并进行测试。如果IntGenerator导致失败，则test()会报错并返回。
 *     所有依赖于IntGenerator对象的EventChecker任务都会检查IntGenerator，已确认任务是否已被取消。
 * 如果generator.isCanceled()的结果是true,run()方法就会返回。任何EventChecker任务都可以调用IntGenerator上的cancel()方法，
 * 这会使其他所有使用该IntGenerator的EventChecker任务优雅地终止。
 *     在这种设计中，多个任务共享了同一个公共资源（即IntGenerator）监听该资源的终止信号。这样便消除了所谓的竞态条件，
 * 即两个以上的任务竞争响应某个条件，并因此发生冲突，或者生成了不一致的结果。
 *     我们必须谨慎地考虑并避免所有可能导致并发系统失败的因素。举例来说，一个任务不能依赖于另一个任务，因为任务的终止顺序是无法保证的。
 * 此外，通过让任务依赖于非任务对象，我们消除了竞态条件的隐患。
 *     通常,我们会假设test()最终总会失败，因为EventChecker任务能够在IntGenerator处于"不正确"的状态时，访问其中的信息。
 * 不过，可能要等到IntGenerator完成很多次循环后，才会检测出该问题，这取决于操作系统的特性，以及实现方面的其他细节。
 * @author kuifir
 * @date 2023/6/30 21:54
 */
public class EvenChecker implements Runnable {

    private IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                System.out.println(val + " not even");
                // 取消所有的EventChecker
                generator.cancel();
            }
        }
    }

    /**
     * 测试任意的IntGenerator
     */
    public static void test(IntGenerator gp, int count) {
        List<CompletableFuture<Void>> checkers = IntStream.range(0, count)
                .mapToObj(i -> new EvenChecker(gp, i))
                .map(CompletableFuture::runAsync)
                .toList();
        checkers.forEach(CompletableFuture::join);
    }

    /**
     * 初始计数值
     */
    public static void test(IntGenerator gp) {
        new TimedAbort(4, "No odd numbers discovered");
        test(gp, 10);
    }
}
