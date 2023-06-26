package com.kuifir.basic.completableFuture;

import cn.hutool.core.date.StopWatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 在没有调用cf.join()的情况下，程序在任务完成前就退出了。
 * 对join()的调用会一直阻塞main()线程的执行，直到cf操作完成，我们可以看到大部分时间确实花在了这个地方。
 * <p></p>
 * 通常来说，CompletableFuture库需要将你的请求的操作连保存为一组<b>回调</b>(callback)。
 * 当第一个后台操作完成后并返回后，第二个后台操作必须接收相应的Machina并开始工作，然后当该操作完成后，下一个操作继续，以此类推。
 * 但是由于这里并非由程序调用栈控制的普通函数调用序列，其调用顺序会丢失，因此改用回调来存储，即一个记录了函数地址的表格。
 *
 * @author kuifir
 * @date 2023/6/25 23:18
 */
public class CompletableApplyAsync {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("start");
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(new Machina(0))
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        stopWatch.start("block");
        System.out.println(cf.join());
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }
}
