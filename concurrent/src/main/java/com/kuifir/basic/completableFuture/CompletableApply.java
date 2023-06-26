package com.kuifir.basic.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * 有趣的是，一旦将{@link Machina}用CompletableFuture包装起来，我们就会发现，
 * 可以通过在CompletableFuture上增加操作来控制其包含的对象：
 * {@link CompletableFuture#thenApply(Function)} ()}用到了一个接受输入并生成输出的Function.
 * 在本例中，work()这个Function返回和输入相同的类型。由此每个得出的CompletableFuture都仍旧是Machina类型，
 * 但是(类似于Stream中的map())Function也可以返回不同的类型，这可以从返回类型看出来。
 * <p></p>
 * 可以从中看出CompletableFuture的一些本质：
 * 当执行某个操作时，它们会自动对其所携带的对象拆开包装，再重新包装。这样你就不会陷入混乱的细节，从而可以大幅简化代码的编写和理解工作。
 * 我们可以消除中间变量，将多个操作串联起来，就像我们使用Stream时那样。
 * @author kuifir
 * @date 2023/6/25 23:02
 * @see CompletedMachina
 */
public class CompletableApply {
    public static void main(String[] args) {
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0));
        CompletableFuture<Machina> cf2 = cf.thenApply(Machina::work);
        CompletableFuture<Machina> cf3 = cf2.thenApply(Machina::work);
        CompletableFuture<Machina> cf4 = cf3.thenApply(Machina::work);
        CompletableFuture<Machina> cf5 = cf4.thenApply(Machina::work);

    }
}
