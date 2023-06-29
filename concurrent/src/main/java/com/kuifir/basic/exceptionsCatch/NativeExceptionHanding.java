package com.kuifir.basic.exceptionsCatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 以下代码产生了和{@link ExceptionThread}相同的结果：一个未捕获的异常。
 * 要解决这个问题，就必须改变Executor生成线程的方式。
 * {@link Thread.UncaughtExceptionHandler}接口会向每个Thead对象添加异常处理程序。
 * 当线程由于未捕获的异常而即将消亡时，{@link Thread.UncaughtExceptionHandler#uncaughtException(Thread, Throwable)}
 * 就会被自动调用。要使用该方法，可以创建一个新的{@link ThreadFactory}类型，它会为它所创建的每个新Thread对象添加一个新的
 * Thread.UncaughtExceptionHandler。
 * 将这个ThreadFactory工厂传入Exectors.newCacheThreadPool方法，该方法会创建新ExecutorService。{@link CaptureUncaughtException}
 * @author kuifir
 * @date 2023/6/29 23:16
 */
public class NativeExceptionHanding {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        try {
            es.execute(new ExceptionThread());
        } catch (RuntimeException e) {
            // 该语句不会执行
            System.out.println("Exception was handled");
        } finally {
            es.shutdown();
        }
    }
}
