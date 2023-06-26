package com.kuifir.basic.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * completedFuture()创建了一个"已完成"的CompletableFuture。
 * 这种future唯一能做的事是get()内部对象，所以乍一看这样做并没有什么用处。
 * <p></p>
 * 注意CompletableFuture的类型为它所包含的对象，这很重要。
 * <p></p>
 * 一般来说，get(0会阻塞正处在等待结果的被调用线程。
 * 该阻塞可以通过{@link InterruptedException}或{@link ExecutionException}来退出。
 * 在本场场景下，由于CompletableFuture已经完成，因此永远不会发生阻塞，当时就能得到结果。
 * 有趣的是，一旦将{@link Machina}用CompletableFuture包装起来，我们就会发现，
 * 可以通过在CompletableFuture上增加操作来控制其包含的对象：{@link CompletableApply}
 * @author kuifir
 * @date 2023/6/25 22:52
 */
public class CompletedMachina {
    public static void main(String[] args) {
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(new Machina(0));
        try {
            Machina m = cf.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
