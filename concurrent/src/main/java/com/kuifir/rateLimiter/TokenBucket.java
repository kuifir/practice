package com.kuifir.rateLimiter;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;

/**
 * 令牌桶算法（Token Bucket）
 * 令牌桶算法的详细描述如下：
 * 1. 令牌以固定的速率添加到令牌桶中，假设限流的速率是 r/ 秒，则令牌每 1/r 秒会添加一个；
 * 2. 假设令牌桶的容量是 b ，如果令牌桶已满，则新的令牌会被丢弃；
 * 3. 请求能够通过限流器的前提是令牌桶中有令牌。
 * <p></p>
 * 这个算法中，限流的速率 r 还是比较容易理解的，但令牌桶的容量 b 该怎么理解呢？
 * b 其实是 burst 的简写，意义是限流器允许的最大突发流量。
 * 比如 b=10，而且令牌桶中的令牌已满，
 * 此时限流器允许 10 个请求同时通过限流器，当然只是突发流量而已，
 * 这 10 个请求会带走 10 个令牌，所以后续的流量只能按照速率 r 通过限流器。
 * <p></p>
 * 令牌桶这个算法，如何用 Java 实现呢？
 * 很可能你的直觉会告诉你生产者 - 消费者模式：
 * 一个生产者线程定时向阻塞队列中添加令牌，而试图通过限流器的线程则作为消费者线程，
 * 只有从阻塞队列中获取到令牌，才允许通过限流器。
 * 这个算法看上去非常完美，而且实现起来非常简单，如果并发量不大，这个实现并没有什么问题。
 * 可实际情况却是使用限流的场景大部分都是高并发场景，而且系统压力已经临近极限了，此时这个实现就有问题了:
 * 问题就出在定时器上，在高并发场景下，当系统压力已经临近极限的时候，
 * 定时器的精度误差会非常大，同时定时器本身会创建调度线程，也会对系统的性能产生影响。
 * <p></p>
 * Guava 实现令牌桶算法，用了一个很简单的办法，其关键是记录并动态计算下一令牌发放的时间。
 *
 * @author kuifir
 * @date 2023/5/21 14:55
 */
public class TokenBucket {
    /**
     * 下一秒产生令牌的时间
     */
    long next = System.nanoTime();
    /**
     * 　发放令牌间隔：纳秒
     */
    long interval = 1000_000_000;
    /**
     * 当前令牌桶中的令牌数量
     */
    long storedPermits = 0;
    /**
     * 令牌桶的容量
     */
    long maxPermits = 3;

    /**
     * 请求时间在下一令牌产生时间之后,则 :
     * 1.重新计算令牌桶中的令牌数
     * 2.将下一个令牌发放时间重置为当前时间
     */
    void resync(long now) {
        if (now > next) {
            // 产生新的令牌数
            long newPermits = (now - next) / interval;
            // 新的令牌增加到令牌桶
            storedPermits = Math.min(maxPermits, storedPermits + newPermits);
            //将下一个令牌发放时间重置为当前时间
            next = now;
        }
    }

    /**
     * 预占令牌，返回能够获取令牌的时间
     *
     * @param now 当前时间
     * @return 该线程能够获取令牌的时间
     */
    synchronized long reserve(long now) {
        resync(now);
        //能够获取令牌的时间
        long at = next;
        //令牌桶中能提供的令牌
        long fb = Math.min(1, storedPermits);
        //令牌净需求：首先减掉令牌桶中的令牌
        long nr = 1 - fb;
        //重新计算下一令牌产生时间
        next = next + nr * interval;
        //重新计算令牌桶中的令牌
        this.storedPermits -= fb;
        return at;
        /* // 桶的大小为1
        // 请求时间在发放令牌之后，直接获取令牌
        // 重新计算下一令牌产生时间
        if (now > next) {
            //将下一令牌产生时间重置为当前时间
            next = now;
        }
        //能够获取令牌的时间
        long at = next;
        //设置下一令牌产生时间
        next += interval;
        //返回线程需要等待的时间
        return at;*/
    }

    /**
     * 申请令牌
     */
    void acquire() {
        //申请令牌时的时间
        long now = System.nanoTime();
        //预占令牌
        long at = reserve(now);
        long waitTime = Math.max(at - now, 0);
        //按照条件等待
        if (waitTime > 0) {
            try {
                TimeUnit.NANOSECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
