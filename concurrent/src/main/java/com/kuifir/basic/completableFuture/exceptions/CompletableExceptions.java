package com.kuifir.basic.completableFuture.exceptions;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 在test()方法中，work()被多次应用于{@link Breakable},所以如果failcount在范围内，则会抛出异常。
 * 不过，再从A到E的测试中，你可以从输出看到异常被抛出，但并不会显露出来。
 * 通过G可以看出，你可以先检查处理过程中是否有异常抛出，而不必真的抛出该异常。
 * 然而，H告诉我们该异常仍符合"完成"的条件，不论其是否真的成功。
 * 代码的最后一部分演示了你可以如何向CompletableFuture插入异常，不论是否出现任何失败。
 * 相比于在合并或获取结果时粗暴地使用try-catch,
 * 我们更倾向于利用CompletableFuture所带来的更为先进的机制来自动地响应异常。{@link CatchCompletableExceptions}
 * 你只需照搬在所有CompletableFuture中看到的方式即可：再调用链中插入CompletableFuture调用。
 * 一共有3个选项：exceptionally()、handle()以及whenComplete():
 * @author kuifir
 * @date 2023/6/27 22:03
 */
public class CompletableExceptions {
    static CompletableFuture<Breakable> test(String id, int failcount){
        return CompletableFuture.completedFuture(new Breakable(id,failcount))
                .thenApply(Breakable::work)
                .thenApply(Breakable::work)
                .thenApply(Breakable::work)
                .thenApply(Breakable::work)
                ;
    }

    public static void main(String[] args) {
        // 异常不会显露出来
        test("A",1);
        test("B",2);
        test("C",3);
        test("D",4);
        test("E",5);
        new Nap(1);

        // ......直到你尝试获取结果
        try {
            // 或者join()
            test("F",2).get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        // 测试异常 true
        System.out.println(test("G", 2).isCompletedExceptionally());
        // 是否完成 true
        System.out.println(test("H", 2).isDone());
        // 强制产生异常
        CompletableFuture<Integer> cfi = new CompletableFuture<>();
        // false
        System.out.println("done?" + cfi.isDone());
        cfi.completeExceptionally(new RuntimeException("forced"));
        try {
            cfi.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
