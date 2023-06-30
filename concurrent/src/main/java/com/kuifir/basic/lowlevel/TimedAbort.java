package com.kuifir.basic.lowlevel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * t秒后终止程序
 * 我们用lambda表达式创建了一个Runnable,通过CompletableFuture的static runAsync()方法来执行。
 * 使用runAsync()的价值是立刻返回调用。因此，TimedAbort不会让任何本来可以完成的任务保持开启状态，
 * 不过如果执行时间太长，它仍然会终止该任务[TimedAbort有时称为守护进程（daemon）].
 * TimedAbort同样允许我们将其restart()（重启），这是为了在需要持续运行某些必要的操作时，可以让程序一直保持运行。
 * @author kuifir
 * @date 2023/6/30 22:10
 */
public class TimedAbort {
    private volatile boolean restart = true;

    public TimedAbort(double t, String msg) {
        CompletableFuture.runAsync(() -> {
            try {
                while (restart) {
                    restart = false;
                    TimeUnit.MILLISECONDS.sleep((int) (1000 * t));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(msg);
            System.exit(0);
        });
    }

    public TimedAbort(double t) {
        this(t, "TimeAbort " + t);
    }

    public void restart() {
        restart = true;
    }
}
