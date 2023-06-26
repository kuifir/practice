package com.kuifir.basic.completableFuture;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.CompletableFuture;

/**
 * @author kuifir
 * @date 2023/6/26 22:40
 */
public class Workable {
    String id;
    final double duration;

    public Workable(String id, double duration) {
        this.id = id;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Workable[" + id + "]";
    }
    public static Workable work(Workable tt){
        new Nap(tt.duration);
        tt.id = tt.id + "W";
        System.out.println(tt);
        return tt;
    }
    public static CompletableFuture<Workable> make(String id, double duration){
        return CompletableFuture.completedFuture(new Workable(id,duration))
                .thenApplyAsync(Workable::work);
    }
}
