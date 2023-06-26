package com.kuifir.basic.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author kuifir
 * @date 2023/6/26 20:47
 */
public class CompletableUtilities {
    /**
     * 获取并展示CF中存储的值
     */
    public static void showr(CompletableFuture<?> c) {
        try {
            System.out.println(c.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 震度无值的CF操作：
     */
    public static void voidr(CompletableFuture<?> c){
        try {
            // 返回void
            c.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
