package com.kuifir.basic.completableFuture.exceptions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 相比于在合并或获取结果时粗暴地使用try-catch,
 * 我们更倾向于利用CompletableFuture所带来的更为先进的机制来自动地响应异常。
 * 你只需照搬在所有CompletableFuture中看到的方式即可：再调用链中插入CompletableFuture调用。
 * 一共有3个选项：
 * - 只有出现异常时，exceptionally()参数才会运行，exceptionally()的限制在于Function返回值类型必须和输入相同。
 * 将一个正确的对象插回流中，可使exceptionally()恢复到工作状态。
 * {@link java.util.concurrent.CompletableFuture#exceptionally(Function)} }
 * - handle()总是会被调用的，而且你必须检查fail是否为true，以确定是否有异常发生。
 * 但是handle()可以生成任意新类型，因此它允许你执行处理，而不是向exceptionally()那样只是恢复
 * {@link java.util.concurrent.CompletableFuture#handle(BiFunction)}
 * - whenComplete()和handle()类似，都必须测试失败情况，但参数是消费者，只会用而不会修改传递中的result对象。
 * {@link java.util.concurrent.CompletableFuture#whenComplete(BiConsumer)}
 *
 * @author kuifir
 * @date 2023/6/27 22:41
 */
public class CatchCompletableExceptions {
    static void handleException(int failcount) {
        // 只有在有异常时才调用该函数，必须生成和输入相同的类型
        CompletableExceptions
                .test("exceptionally", failcount)
                .exceptionally((ex) -> {
                    // Function
                    if (ex == null) {
                        System.out.println("I don't get it yet");
                    }
                    return new Breakable(ex.getMessage(), 0);
                })
                .thenAccept(str -> System.out.println("result:" + str));
        // 创建新结果
        CompletableExceptions
                .test("handle", failcount)
                .handle((result, fail) -> {
                    // BiFunction
                    if (fail != null) {
                        return "Failure recovery object";
                    } else {
                        return result + " is good";
                    }
                })
                .thenAccept(str -> System.out.println("result: " + str));
        // 做了一些逻辑处理，但仍向下传递相同的结果
        CompletableExceptions
                .test("whenComplete", failcount)
                .whenComplete((result, fail) -> {
                    // BiConsumer
                    if (fail != null) {
                        System.out.println("It failed");
                    } else {
                        System.out.println(result + " OK");
                    }
                })
                // 下面一行发生异常时不会执行
                .thenAccept(r -> System.out.println("result:" + r));
    }

    public static void main(String[] args) {
        System.out.println("******Failure Mode******");
        handleException(2);
        System.out.println("*******Success Mode*****");
        handleException(0);
    }
}
