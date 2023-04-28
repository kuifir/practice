package com.kuifir.juc.threadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可以把线程池类比为一个项目组，而线程就是项目组的成员。
 * - corePoolSize：表示线程池保有的最小线程数。有些项目很闲，但是也不能把人都撤了，至少要留 corePoolSize 个人坚守阵地。
 * - maximumPoolSize：表示线程池创建的最大线程数。当项目很忙时，就需要加人，但是也不能无限制地加，最多就加到 maximumPoolSize 个人。
 * 当项目闲下来时，就要撤人了，最多能撤到 corePoolSize 个人。
 * - keepAliveTime & unit：上面提到项目根据忙闲来增减人员，那在编程世界里，如何定义忙和闲呢？
 * 很简单，一个线程如果在一段时间内，都没有执行任务，说明很闲，keepAliveTime 和 unit 就是用来定义这个“一段时间”的参数。
 * 也就是说，如果一个线程空闲了keepAliveTime & unit这么久，而且线程池的线程数大于 corePoolSize ，那么这个空闲的线程就要被回收了。
 * - workQueue：工作队列，和上面示例代码的工作队列同义。
 * - threadFactory：通过这个参数你可以自定义如何创建线程，例如你可以给线程指定一个有意义的名字。
 * - handler：通过这个参数你可以自定义任务的拒绝策略。
 * 如果线程池中所有的线程都在忙碌，并且工作队列也满了（前提是工作队列是有界队列），那么此时提交任务，线程池就会拒绝接收。
 * 至于拒绝的策略，你可以通过 handler 这个参数来指定。
 * ThreadPoolExecutor 已经提供了以下 4 种策略。
 * 1. CallerRunsPolicy：提交任务的线程自己去执行该任务。
 * 2. AbortPolicy：默认的拒绝策略，会 throws RejectedExecutionException。
 * 3. DiscardPolicy：直接丢弃任务，没有任何异常抛出。
 * 4. DiscardOldestPolicy：丢弃最老的任务，其实就是把最早进入工作队列的任务丢弃，然后把新任务加入到工作队列。
 * 5. Java 在 1.6 版本还增加了 allowCoreThreadTimeOut(boolean value) 方法，它可以让所有线程都支持超时，这意味着如果项目很闲，就会将项目组的成员都撤走。
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                100,
                120,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),// AbortPolicy：默认的拒绝策略，会 throws RejectedExecutionException。
                new CustomThreadFactory(null, "test"),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(()-> {
                System.out.println(integer.getAndIncrement());
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

    /**
     * 自定义线程池中线程的名字
     */
    static class CustomThreadFactory implements ThreadFactory {
        /**
         * 线程池编号（static修饰）(容器里面所有线程池的数量)
         */
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

        /**
         * 线程编号(当前线程池线程的数量)
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        /**
         * 线程组
         */
        private final ThreadGroup group;
        /**
         * 业务名称前缀
         */
        private final String namePrefix;

        /**
         * 重写线程名称（获取线程池编号，线程编号，线程组）
         *
         * @param namePrefix 你需要指定的业务名称
         */
        CustomThreadFactory(ThreadGroup group, String namePrefix) {
            this.group = group == null ? Thread.currentThread().getThreadGroup() : group;
            this.namePrefix = namePrefix + "-poolNumber:" + POOL_NUMBER.getAndIncrement() + "-threadNumber:";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    //方便dump的时候排查（重写线程名称）
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}

