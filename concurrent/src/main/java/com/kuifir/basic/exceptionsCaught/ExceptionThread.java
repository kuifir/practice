package com.kuifir.basic.exceptionsCaught;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 将main()方法体包裹在try-catch块中也无法成功运行：{@link NativeExceptionHanding}
 * @author kuifir
 * @date 2023/6/29 23:13
 */
public class ExceptionThread implements Runnable{

    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new ExceptionThread());
        es.shutdown();
    }
}
