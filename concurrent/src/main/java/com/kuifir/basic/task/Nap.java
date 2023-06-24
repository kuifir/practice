package com.kuifir.basic.task;

import java.util.concurrent.TimeUnit;

/**
 * 一个实现睡眠的类
 *
 * @author kuifir
 * @date 2023/6/24 21:47
 */
public class Nap {
    public Nap(double t) {
        // 秒
        try {
            // 会获得"当前线程",并让它按参数中传入的时长睡眠，这意味着该线程将被挂起。
            // 但这并不意味着底层的处理器停止了。操作系统会切换到某些其他任务。
            // 操作系统的任务管理器会定期检查sleep()是否时间到了。如果时间到了，线程会被唤醒，并继续分配给处理器时间。
            TimeUnit.MILLISECONDS.sleep((int) (1000 * t));
        } catch (InterruptedException e) {
            // sleep()会抛出InterruptedException异常，这是Java早期设计的产物，通过立刻跳出任务来终止它们。
            // 由于这容易产生不稳定的状态，后续便不再鼓励如此终止任务了。
            // 然而我们必须捕获各种情况下的该异常，以应对必要或不可控的任务终止。
            throw new RuntimeException(e);
        }
    }

    public Nap(double t, String msg) {
        this(t);
        System.out.println(msg);
    }
}
