package com.kuifir.juc.countDownLatchAndCyclicBarrier;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 将顺序的逻辑用并行实现，并且按照一定的逻辑顺序
 * <p/>
 * 需求：对账操作，用户通过在线商城下单，会生成电子订单，保存在订单库；
 * 之后物流会生成派送单给用户发货，派送单保存在派送单库。
 * 为了防止漏派送或者重复派送，对账系统每天还会校验是否存在异常订单。
 * 目前对账系统的处理逻辑是首先查询订单，然后查询派送单，之后对比订单和派送单，将差异写入差异库
 * <pre>{@code
 * while(存在未对账订单)
 * {
 * // 查询未对账订单
 * pos = getPOrders();
 * // 查询派送单
 * dos = getDOrders();
 * // 执行对账操作
 * diff = check(pos, dos);
 * // 差异写入差异库
 * save(diff);
 * }
 * }</pre>
 */
public class ThreadCoordination {
    // 订单队列
    static Vector<Integer> pos = new Vector<>();
    // 派送单队列
    static Vector<Integer> dos = new Vector<>();
    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
//        SerialWithThread();
//        SerialWithThreadPool(); 会发生错误，两个列表可能会不同步，线程不会结束join()方法不能使用
//        SerialWithCountDownLatch(); // 此时两次查询是异步的，但是，查询后的校验和保存操作是同步的
        SerialWithCyclicBarrier();


    }

    private static void SerialWithThread() throws InterruptedException {

        while (true) {
            // 查询未对账订单
            Thread T1 = new Thread(() -> {
                pos = getPOrders();
            });
            T1.start();
            // 查询派送单
            Thread T2 = new Thread(() -> {
                dos = getDOrders();
            });
            T2.start();
            // 等待T1、T2结束
            T1.join();
            T2.join();
            // 执行对账操作
            Map<Integer, Integer> diff = check(pos, dos);
            // 差异写入差异库
            save(diff);
        }
    }

    private static void SerialWithThreadPool() {

        // 创建2个线程的线程池
        Executor executor =
                Executors.newFixedThreadPool(2);
        while (true) {
            // 查询未对账订单
            executor.execute(() -> {
                pos = getPOrders();
            });
            // 查询派送单
            executor.execute(() -> {
                dos = getDOrders();
            });

            /* ？？如何实现等待？？*/
            // 使用了线程池后，线程不会结束join()方法不能使用

            // 执行对账操作
            Map<Integer, Integer> diff = check(pos, dos);
            // 差异写入差异库
            save(diff);
        }
    }

    private static void SerialWithCountDownLatch() {

// 创建2个线程的线程池
        Executor executor =
                Executors.newFixedThreadPool(2);
        while (true) {
            // 计数器初始化为2
            CountDownLatch latch =
                    new CountDownLatch(2);
            // 查询未对账订单
            executor.execute(() -> {
                pos = getPOrders();
                latch.countDown();
            });
            // 查询派送单
            executor.execute(() -> {
                dos = getDOrders();
                latch.countDown();
            });

            // 等待两个查询操作结束
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 执行对账操作
            Map<Integer, Integer> diff = check(pos, dos);
            // 差异写入差异库
            save(diff);
        }
    }

    private static void SerialWithCyclicBarrier() {
        // 订单队列
        Vector<Integer> pos2 = new Vector<>();
        // 派送单队列
        Vector<Integer> dos2 = new Vector<>();
        Executor executor = Executors.newFixedThreadPool(1);
        final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            executor.execute(() -> save(check(pos2, dos2)));
        });
        Thread T1 = new Thread(() -> {
            while (true) {
                // 查询订单库
                pos2.addAll(getPOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        T1.start();
        // 循环查询运单库
        Thread T2 = new Thread(() -> {
            while (true)//存在未对账订单
            {      // 查询运单库
                dos2.addAll(getDOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        T2.start();
    }


    private static void save(Map<Integer, Integer> diff) {
        for (Map.Entry<Integer, Integer> integerIntegerEntry : diff.entrySet()) {
            System.out.println(integerIntegerEntry.getKey() + "--->" + integerIntegerEntry.getValue());
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("一个过程完成=========");
    }

    private static Map<Integer, Integer> check(Vector<Integer> pos, Vector<Integer> dos) {
        System.out.println(pos.size());
        System.out.println(dos.size());
        Map<Integer, Integer> diff = new HashMap<>();
        for (int i = 0; i < pos.size(); i++) {
            diff.put(pos.get(i), dos.get(i));
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return diff;
    }

    private static Vector<Integer> getDOrders() {
        pos.clear();
        IntStream.range(1, 5).forEach(i -> pos.add(random.nextInt(10)));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pos;
    }

    private static Vector<Integer> getPOrders() {
        dos.clear();
        IntStream.range(1, 5).forEach(i -> dos.add(random.nextInt(10)));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dos;
    }

}
