package com.kuifir.basic.exceptionsCaught;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 下面这个示例可能会出乎你意料：
 * 这段代码什么都不会输出(不过如果你将submit()替换为execute()，便会看到异常{@link ExceptionThread})。
 * 这说明在线程内部抛出异常很需要技巧，也需要特别仔细地操作。
 * <p></p>
 * 你无法捕获已逃离线程的异常，一旦异常逃逸到任务的run()方法之外，
 * 便会扩散到控制台，除非采用专门的步骤来捕获这种不正确的异常。{@link ExceptionThread}
 * 将main()方法体包裹在try-catch块中也无法成功运行：{@link NativeExceptionHanding}
 * <p></p>
 * {@link NativeExceptionHanding}产生了和{@link ExceptionThread}相同的结果：一个未捕获的异常。
 * 要解决这个问题，就必须改变Executor生成线程的方式。
 * {@link Thread.UncaughtExceptionHandler}接口会向每个Thead对象添加异常处理程序。
 * 当线程由于未捕获的异常而即将消亡时，{@link Thread.UncaughtExceptionHandler#uncaughtException(Thread, Throwable)}
 * 就会被自动调用。要使用该方法，可以创建一个新的{@link ThreadFactory}类型，它会为它所创建的每个新Thread对象添加一个新的
 * Thread.UncaughtExceptionHandler。
 * 将这个ThreadFactory工厂传入Exectors.newCacheThreadPool方法，该方法会创建新ExecutorService。{@link CaptureUncaughtException}
 * {@link CaptureUncaughtException}基于具体问题具体分析的方式设置处理器。
 * 如果你确定要在所有的地方都应用同一个异常处理程序，更简单的方法是设置默认未捕获异常处理程序，
 * 该处理器会在Thread内部设置一个静态字段：{@link SettingDefaultHandler}
 * @author kuifir
 * @date 2023/6/29 23:04
 */
public class SwallowedException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(()->{
            throw new RuntimeException();
        });
        exec.shutdown();
    }
}
