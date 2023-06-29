package com.kuifir.basic.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * "工作窃取"线程池(WorkStealingPool):
 * 这是一种能基于所有可用处理器(如JVM可检测出的)自动创建线程池的ExecutorService：
 * 工作窃取(Work Stealing)算法使得已完成自身输入队列中所有工作项的线程可以"窃取"其他队列中的工作项。
 * 该算法的目的是在执行计算密集型任务时能够跨处理器分发工作项，由此最大化所有可用处理器的利用率，
 * Java的fork/join框架中同样也用到了该算法。
 * @author kuifir
 * @date 2023/6/29 22:52
 */
public class WorkStealingPool {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService exec = Executors.newWorkStealingPool();
        IntStream.range(0, 10)
                .mapToObj(n -> new ShowThread())
                .forEach(exec::execute);
        exec.awaitTermination(1, TimeUnit.SECONDS);
    }
}

class ShowThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
