package com.kuifir.basic.completableFuture.useCase;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.CompletableFuture;

/**
 * @author kuifir
 * @date 2023/6/26 23:59
 */
public class FrostedCake {
    public FrostedCake(Baked baked,Frosting frosting){
        new Nap(0.1);
    }

    @Override
    public String toString() {
        return "FrostedCake";
    }

    public static void main(String[] args) {
        Baked.batch().forEach(baked-> baked
                .thenCombineAsync(Frosting.make(), FrostedCake::new)
                .thenAcceptAsync(System.out::println)
                .join());
    }
}
final class Frosting{
    private Frosting(){};
    static CompletableFuture<Frosting> make(){
        new Nap(0.1);
        return CompletableFuture.completedFuture(new Frosting());
    }
}