package com.kuifir.basic.completableFuture;

import java.util.concurrent.CompletableFuture;

import static com.kuifir.basic.completableFuture.CompletableUtilities.showr;
import static com.kuifir.basic.completableFuture.CompletableUtilities.voidr;

/**
 * @author kuifir
 * @date 2023/6/26 20:52
 */
public class CompletableOperations {
    static CompletableFuture<Integer> cfi(int i) {
        return CompletableFuture.completedFuture(i);
    }

    public static void main(String[] args) {
        showr(cfi(1));
        // runAsync() 接收Runnable,没有返回值
        voidr(cfi(2).runAsync(() -> System.out.println("runAsync")));
        // runAsync() 和thenRunAsync()似乎完全一样。
        voidr(cfi(3).thenRunAsync(() -> System.out.println("thenRunAsync")));
        // runAsync()是静态方法，所以一般不会想在cfi(2)中调用它
        voidr(CompletableFuture.runAsync(() -> System.out.println("runAsync is static")));
        // supplyAsync()同样也是静态的，但它依赖的是Supplier而不是Runnable。
        // 并且会生成CompletableFuture<Integer>而不是并且会生成CompletableFuture<Void>
        showr(CompletableFuture.supplyAsync(() -> 99));

        // then系列方法针对已有的ComputableFuture<Integer>进行操作.
        // 不同于thenRunAsync()，用于cfi(4)、cfi(5)、cfi(6)的系列then方法接收未包装的Integer类型作为参数。
        // thenAcceptAsync()，接收Consumer作为参数，所以不会返回结果。
        // thenApplyAsync()，接收Function作为参数，因此会返回结果（可以是和参数不同的返回类型）。
        // thenComposeAsync()和thenApplyAsync()非常像，只是它的Function必须返回已在CompletableFuture中被包装后的结果。
        voidr(cfi(4).thenAcceptAsync(i -> System.out.println("thenAcceptAsync: " + i)));
        showr(cfi(5).thenApplyAsync(i -> i + 42));
        showr(cfi(6).thenComposeAsync(i -> cfi(i + 99)));

        // obtrudeValue()强制输入一个值做为结果。
        CompletableFuture<Integer> c = cfi(7);
        c.obtrudeValue(111);
        showr(c);
        // toCompletableFuture()以从当前的CompletionStage生成CompletableFuture。
        showr(cfi(8).toCompletableFuture());
        // complete()你可以如何通过传入结果来让一个future完成执行(而obtrudeValue()则可以强制用自己的结果来替换这个结果)
        c = new CompletableFuture<>();
        c.complete(9);
        showr(c);
        // 如果你cancel()取消了CompletableFuture，它同样会变成"已完成"，并且是特殊情况下的已完成（completed exceptionally）
        c = new CompletableFuture<>();
        c.cancel(true);
        System.out.println("cancelled: " + c.isCancelled());
        System.out.println("completed exceptionally: " + c.isCompletedExceptionally());
        // getNow()方法要么返回CompletableFuture的完整值，要么返回getNow()的替代参数(如果该future尚未完成)。
        c = new CompletableFuture<>();
        System.out.println(c.getNow(777));
        // dependents的概念：如果将两次对CompletableFuture的thenApplyAsync()调用连在一起，dependents的数量还是一个。
        // 但是如果我们直接将另一个thenApplyAsync()添加到c,那么就有了两个dependent:两个连续的调用和一个额外的调用。
        // 这说明了一个单独的CompletionStage可以在其完成后，基于它的结果fork出多个任务。
        c = new CompletableFuture<>();
        c.thenApplyAsync(i -> i + 42)
                .thenApplyAsync(i -> i * 42);
        System.out.println("dependents: " + c.getNumberOfDependents());
        c.thenApplyAsync(i -> i / 2);
        System.out.println("dependents: " + c.getNumberOfDependents());


    }
}

