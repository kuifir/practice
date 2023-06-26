package com.kuifir.basic.completableFuture;

import cn.hutool.core.date.StopWatch;

import java.util.concurrent.CompletableFuture;

/**
 * 我们可以消除中间变量，将多个操作串联起来，就像我们使用Stream时那样。
 * 使用CompletableFuture有个重要的好处——会促使我们应用自私儿童原则（什么都不共享）。
 * 默认情况下，通过thenApply()来应用函数并不会产生任何通信，它只是接收参数并返回结果。这是函数式编程的基础之一，
 * 也是它如此是合并发的原因之一。并行流和CompletableFuture便是基于这些原则设计的。
 * 只要你决定怎样都不分享任何数据（分享很容易发生，甚至会意外发生），就可以写出相当安全的并发程序。
 * 操作是通过调用thenApply()开始的。本例中，CompletableFuture的创建过程会等到所有任务都完成后才会完成。
 * 虽然这有时很有用，但更多的价值还是在于可以开启所有的任务，然后你就可以在任务运行时继续做其他的事情。
 * 我们通过在操作最后增加Async来实现效果{@link CompletableApplyAsync}
 * @author kuifir
 * @date 2023/6/25 23:15
 */
public class CompletableApplyChained {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(new Machina(0))
                        .thenApply(Machina::work)
                        .thenApply(Machina::work)
                        .thenApply(Machina::work)
                        .thenApply(Machina::work);
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }
}
