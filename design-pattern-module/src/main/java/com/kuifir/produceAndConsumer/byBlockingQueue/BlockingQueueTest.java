package com.kuifir.produceAndConsumer.byBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {
    private int maxInventory = 10; // 最大库存
    private BlockingQueue<String> product = new LinkedBlockingQueue(maxInventory);

    public void produce(String element){
        try {
            product.put(element);
            System.out.printf("放入一个商品库存: %s,总库存为：%d %n", element, product.size());
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void consumer(){
        try {
            String take = product.take();
            System.out.printf("消费一个商品库存: %s,总库存为：%d %n", take, product.size());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class Produce implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                produce("商品" + i);
            }
        }
    }

    /**
     * 消费者
     */
    private class Consumer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                consumer();
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueueTest blockingQueueTest = new BlockingQueueTest();
        new Thread(blockingQueueTest.new Produce()).start();
        new Thread(blockingQueueTest.new Consumer()).start();
        new Thread(blockingQueueTest.new Produce()).start();
        new Thread(blockingQueueTest.new Consumer()).start();
    }
}
