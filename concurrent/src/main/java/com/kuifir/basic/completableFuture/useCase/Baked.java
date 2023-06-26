package com.kuifir.basic.completableFuture.useCase;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @author kuifir
 * @date 2023/6/26 23:50
 */
public class Baked {
    static class Pan {
    }

    static Pan pan(Batter batter) {
        new Nap(0.1);
        return new Pan();
    }

    static Baked heat(Pan p) {
        new Nap(0.1);
        return new Baked();
    }

    static CompletableFuture<Baked> bake(CompletableFuture<Batter> cfb) {
        return cfb.thenApplyAsync(Baked::pan)
                .thenApplyAsync(Baked::heat);
    }

    public static Stream<CompletableFuture<Baked>> batch() {
        CompletableFuture<Batter> mix = Batter.mix();
        return Stream.of(bake(mix), bake(mix),
                bake(mix), bake(mix));
    }
}
