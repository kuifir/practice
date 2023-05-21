package com.kuifir.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link RateLimiter}的使用
 * 假设我们有一个线程池，它每秒只能处理两个任务，如果提交的任务过快，可能导致系统不稳定，这个时候就需要用到限流。
 * 在下面的示例代码中，我们创建了一个流速为 2 个请求 / 秒的限流器，这里的流速该怎么理解呢？
 * 直观地看，2 个请求 / 秒指的是每秒最多允许 2 个请求通过限流器，
 * 其实在 Guava 中，流速还有更深一层的意思：是一种匀速的概念，2 个请求 / 秒等价于 1 个请求 /500 毫秒。
 * 在向线程池提交任务之前，调用 acquire() 方法就能起到限流的作用。通过示例代码的执行结果，任务提交到线程池的时间间隔基本上稳定在 500 毫秒。
 *<p></p>
 * Guava 的限流器使用上还是很简单的，那它是如何实现的呢？
 * Guava 采用的是令牌桶算法{@link TokenBucket}，其核心是要想通过限流器，必须拿到令牌。
 * 也就是说，只要我们能够限制发放令牌的速率，那么就能控制流速了
 * <p></p>
 * 经典的限流算法有两个，一个是令牌桶算法（Token Bucket），另一个是漏桶算法（Leaky Bucket）。
 * 令牌桶算法是定时向令牌桶发送令牌，请求能够从令牌桶中拿到令牌，然后才能通过限流器；
 * 而漏桶算法里，请求就像水一样注入漏桶，漏桶会按照一定的速率自动将水漏掉，只有漏桶里还能注入水的时候，请求才能通过限流器。
 * 令牌桶算法和漏桶算法很像一个硬币的正反面，所以你可以参考令牌桶算法的实现来实现漏桶算法。
 * @see RateLimiter
 * @author kuifir
 * @date 2023/5/21 14:26
 */
public class RateLimiterUseCase {
    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(2.0);
        //执行任务的线程池
        ExecutorService es = Executors.newFixedThreadPool(2);
        //记录上一次执行时间
        AtomicLong prev = new AtomicLong(System.nanoTime());
        //测试执行20次
        for (int i = 0; i < 20; i++) {
            //限流器限流
            limiter.acquire();
            //提交任务异步执行
            es.execute(() -> {
                long cur = System.nanoTime();
                //打印时间间隔：毫秒
                System.out.println((cur - prev.get()) / 1000_000);
                prev.set(cur);
            });
        }
        es.shutdown();
    }
}
