package com.kuifir.basic.task;

/**
 * 每个任务都会使val自增100次
 * @author kuifir
 * @date 2023/6/24 22:27
 */
public class InterferingTask implements Runnable {
    final int id;
    private static Integer val = 0;

    public InterferingTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            val++;
        }
        System.out.println(id + " " + Thread.currentThread().getName()+" " + val);
    }
}
