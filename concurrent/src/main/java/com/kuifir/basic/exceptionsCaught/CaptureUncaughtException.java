package com.kuifir.basic.exceptionsCaught;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 从附加的跟踪代码可以验证得知，工厂所创建的线程获得了新的{@link Thread.UncaughtExceptionHandler}
 * 未捕获的的异常现在被uncaughtException捕获了
 * {Java17 未测试成功}
 * 本例基于具体问题具体分析的方式设置处理器。
 * 如果你确定要在所有的地方都应用同一个异常处理程序，更简单的方法是设置默认未捕获异常处理程序，
 * 该处理器会在Thread内部设置一个静态字段：{@link SettingDefaultHandler}
 * @author kuifir
 * @date 2023/6/29 23:27
 */
public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
        exec.shutdown();
    }
}

class ExceptionThread2 implements Runnable {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t.getName());
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh= " + t.getUncaughtExceptionHandler());
        return t;
    }
}