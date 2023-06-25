package com.kuifir.basic.quit;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Java最初的设计提供了某种机制（为了向后兼容，该机制仍然存在）来<b>中断</b>(interrupt)正在运行的任务，
 * 中断机制在阻塞方面存在一些问题。
 * 中断任务是混乱复杂的，因为你必须理解所有可能导致中断发生的状态，以及可能导致的数据丢失。
 * 中断被认为是一种反模式，但由于向后兼容设计的残留，我们仍然不得不捕获{@link InterruptedException}异常
 * <p></p>
 * 终止任务最好的方法是设置一个任务会定期检查的标识，由此任务可以通过自己的关闭流程来优雅的终止。
 * 你可以请求任务再合适的时候自行终止，而不是在某一时刻突然拔掉任务的插头。这样的结果永远都比中断更好，而且代码也更清晰、更好理解。
 * <p></p>
 * 像这样终止任务听起来足够简单：设置一个任务可见的boolean标识。修改任务，让定期检查该标识，并优雅的终止。你确实只需要这么做，
 * 但还是有麻烦的地方——我们的老敌人——共享可变状态。如果该标识可以被其他任务操作，那么就有可能产生冲突。
 * <p></p>
 * 如果你研究Java的相关文献，会发现很多解决该问题的方法，最常见的是通过volatile关键字。
 * 我们应该是用更简单的技术，以避免volatile所带来的所有不确定性。
 * Java5引入了Atomic类，它提供了一组类型,让你可以无需担心并发问题，并放心使用。
 * <p></p>
 * 虽然多个任务可以成功地在同一个实例中调用{@link QuittableTask#quit()},
 * 但{@link AtomicBoolean}阻止了多个任务同时修改running，由此保证quit()方法是线程安全的。
 * @author kuifir
 * @date 2023/6/25 21:54
 */
public class QuittableTask implements Runnable {
    final int id;

    public QuittableTask(int id) {
        this.id = id;
    }

    private AtomicBoolean running = new AtomicBoolean(true);

    public void quit() {
        running.set(false);
    }

    @Override
    public void run() {
        // 只要running标识还是true,该任务的run()方法就会持续执行
        while (running.get()) {
            new Nap(0.1);
        }
        // 任务退出后才会执行下面输出
        System.out.print(id + "　");
    }
}
