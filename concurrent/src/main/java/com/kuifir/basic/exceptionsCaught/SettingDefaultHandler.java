package com.kuifir.basic.exceptionsCaught;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 只有在为线程专门配置的未捕获异常处理程序不存在的时候该处理器才会被调用。
 * 系统会检查是否存在为线程专门配置的处理器，如果不存在，则会检查该线程组是否专门实现了其uncaughtException()方法。
 * 如果没有，则会调用defaultUncaughtExceptionHandler
 * 可以将该方法和CompletableFuture中改进的方法进行比较。
 * @author kuifir
 * @date 2023/6/29 23:47
 */
public class SettingDefaultHandler {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new ExceptionThread());
        es.shutdown();
    }
}
