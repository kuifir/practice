package com.kuifir.basic.atomics.josh;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.CompletableFuture;

/**
 * SerialNumberChecker中含有一个持有最新一批序列号的CircularSet,以用于填充CircularSet并确保这些序列号唯一的run()方法：
 *
 * @author kuifir
 * @date 2023/7/1 15:42
 */
public class SerialNumberChecker implements Runnable {
    private CircularSet serials = new CircularSet(1000);
    private SerialNumbers producer;

    public SerialNumberChecker(SerialNumbers producer) {
        this.producer = producer;
    }

    @Override
    public void run() {
        while (true) {
            int serial = producer.nextSerialNumber();
            if (serials.contains(serial)) {
                System.out.println("Duplicate " + serial);
                System.exit(0);
            }
            serials.add(serial);
        }
    }

    static void test(SerialNumbers producer) {
        for (int i = 0; i < 10; i++) {
            CompletableFuture.runAsync(new SerialNumberChecker(producer));
        }
        new Nap(4, "No duplicates detected");
    }
}
