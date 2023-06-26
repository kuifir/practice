package com.kuifir.basic.completableFuture.useCase;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.CompletableFuture;

/**
 * 为了示范如何通过CompletableFuture来将一些列的操作捆绑到一起，我们来模拟制作蛋糕的过程。
 * 第一步是准备配料(ingredient)，并将它们混入面糊{@link Batter}中：
 * 每种配料都需要一些时间来准备。allOf()会等待所有的配料准备完毕，然后再花些时间将他们混合并放入面糊中。
 * 下一步是将一份面糊分摊到4个平底锅中，然后开始烘焙。
 * 成品会以CompletableFuture类型的Stream返回：{@link Baked}
 * 最后创建一份Frosting(糖霜)，并将其洒在蛋糕上。{@link FrostedCake}{@link Frosting}
 * @author kuifir
 * @date 2023/6/26 23:31
 */
public class Batter {
    static class Eggs {
    }

    static class Milk {
    }

    static class Sugar {
    }

    static class Flour {
    }

    static <T> T prepare(T ingerdient) {
        // 准备每个配料都需要时间
        new Nap(0.1);
        return ingerdient;
    }

    static <T> CompletableFuture<T> prep(T ingerdient) {
        return CompletableFuture
                .completedFuture(ingerdient)
                .thenApplyAsync(Batter::prepare);
    }
    public static CompletableFuture<Batter> mix(){
        CompletableFuture<Eggs> eggs = prep(new Eggs());
        CompletableFuture<Milk> milk = prep(new Milk());
        CompletableFuture<Sugar> sugar = prep(new Sugar());
        CompletableFuture<Flour> flour = prep(new Flour());
        return CompletableFuture.allOf(eggs,milk,sugar,flour)
                .thenRunAsync(()->new Nap(0.1))
                .thenApplyAsync(w-> new Batter());

    }

}
