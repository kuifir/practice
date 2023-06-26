package com.kuifir.basic.completableFuture;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.CompletableFuture;

import static com.kuifir.basic.completableFuture.CompletableUtilities.showr;
import static com.kuifir.basic.completableFuture.CompletableUtilities.voidr;

/**
 * CompletableFuture中的第二类方法接收两个CompletableFuture作为参数，并以多种方式将其合并。
 * 一般来说一个CompletableFuture会先于另一个执行完成，两者看起来就像在彼此竞争。
 * 这些方法使你可以用不同的方式处理结果。
 *
 * @author kuifir
 * @date 2023/6/26 22:45
 */
public class DualCompletableOperations {
    static CompletableFuture<Workable> cfA, cfB;

    static void init() {
        cfA = Workable.make("A", 0.15);
        cfB = Workable.make("B", 0.10);
    }

    static void join() {
        cfA.join();
        cfB.join();
        System.out.println("****************");
    }

    public static void main(String[] args) {
        init();
        voidr(cfA.runAfterEither(cfB, () -> System.out.println("runAfterEither")));
        join();

        init();
        voidr(cfA.runAfterBoth(cfB, () -> System.out.println("runAfterBoth")));
        join();

        init();
        showr(cfA.applyToEitherAsync(cfB, w -> {
            System.out.println("applyToEither: " + w);
            return w;
        }));
        join();

        init();
        voidr(cfA.acceptEitherAsync(cfB, w -> {
            System.out.println("acceptEitherAsync: " + w);
        }));
        join();

        init();
        voidr(cfA.thenAcceptBothAsync(cfB, (w1, w2) -> {
            System.out.println("thenAcceptBoth: " + w1 + "," + w2);
        }));
        join();

        init();
        showr(cfA.thenCombineAsync(cfB, (w1, w2) -> {
            System.out.println("thenComposeAsync: " + w1 + "," + w2);
            return w1;
        }));
        join();

        init();
        CompletableFuture<Workable> cfC = Workable.make("C", 0.06),
                cfD = Workable.make("D", 0.08);
        CompletableFuture.anyOf(cfA, cfB, cfC, cfD)
                .thenRunAsync(() -> System.out.println("anyOf"));
        join();

        init();
        cfC = Workable.make("C", 0.06);
        cfD = Workable.make("D", 0.08);
        CompletableFuture.allOf(cfA, cfB, cfC, cfD)
                .thenRunAsync(() -> System.out.println("allOf"));
        join();
    }
}
