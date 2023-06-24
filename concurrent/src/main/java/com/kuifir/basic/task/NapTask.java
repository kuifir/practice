package com.kuifir.basic.task;

/**
 * Java5专门新增了一些类来处理<b>线程池</b>。你不再需要为每个不同的任务类型都创建一个新的Thread子类，
 * 而只需将任务创建为一个单独的类型，然后传递给某个{@link java.util.concurrent.ExecutorService}来运行该任务。
 * 该ExecutorService会为你管理多线程，并且在县城完成任务后不会丢弃，而是回收它们。
 * <p></p>
 * {@link NapTask} 会创建一个几乎什么都不做的任务。它会"睡眠"（挂起执行）100毫秒
 * ，然后显示它的标识符和正在执行任务的Thread名称，最后结束。
 * @author kuifir
 * @date 2023/6/24 21:46
 */
public class NapTask implements Runnable {
    final int id;

    public NapTask(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        new Nap(0.1);
        System.out.println(this + " " + Thread.currentThread().getName());
    }

    @Override
    public String toString() {
        return "NapTask[" + id + "]";
    }
}
