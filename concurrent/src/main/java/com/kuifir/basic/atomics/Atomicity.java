package com.kuifir.basic.atomics;

import com.kuifir.basic.lowlevel.TimedAbort;

import java.util.concurrent.CompletableFuture;

/**
 * @author kuifir
 * @date 2023/6/30 23:29
 */
public class Atomicity {
    public static void test(IntTestable it){
        new TimedAbort(4, "No failures found");
        CompletableFuture.runAsync(it);
        while (true){
            int val = it.getAsInt();
            if (val%2 != 0){
                System.out.println("fail with: " + val);
                System.exit(0);
            }
        }
    }
}
