package com.kuifir.volatiles;

/**
 * volatile关键字含义：会确保我们对于这个变量的读取和写入，都一定会同步到主内存里，而不是从 Cache 里面读取
 * <p></p>
 * <ul>
 *     <li>使用了 volatile 关键字的例子里，因为所有数据的读和写都来自主内存。
 *     那么自然地，我们的 ChangeMaker 和 ChangeListener 之间，看到的 COUNTER 值就是一样的。</li>
 *     <li>到了第二段进行小小修改的时候，我们去掉了 volatile 关键字。
 *     这个时候，ChangeListener 又是一个忙等待的循环，
 *     它尝试不停地获取 COUNTER 的值，这样就会从当前线程的“Cache”里面获取。
 *     于是，这个线程就没有时间从主内存里面同步更新后的 COUNTER 值。
 *     这样，它就一直卡死在 COUNTER=0 的死循环上了。</li>
 *     <li>而到了我们再次修改的第三段代码里面，虽然还是没有使用 volatile 关键字，
 *     但是短短 5ms 的 Thead.Sleep 给了这个线程喘息之机。
 *     既然这个线程没有这么忙了，它也就有机会把最新的数据从主内存同步到自己的高速缓存里面了。
 *     于是，ChangeListener 在下一次查看 COUNTER 值的时候，
 *     就能看到 ChangeMaker 造成的变化了。</li>
 * </ul>
 * 对volatile两种常见的错误理解：
 * <ul>
 *     <li>把 volatile 当成一种锁机制，认为给变量加上了 volatile，就好像是给函数加了 synchronized 关键字一样，不同的线程对于特定变量的访问会去加锁</li>
 *     <li>把 volatile 当成一种原子化的操作机制，认为加了 volatile 之后，对于一个变量的自增的操作就会变成原子性的了。</li>
 * </ul>
 * <pre>{@code
 *          // 一种错误的理解，是把volatile关键词，当成是一个锁，可以把long/double这样的数的操作自动加锁
 *          private volatile long synchronizedValue = 0;
 *          // 另一种错误的理解，是把volatile关键词，当成可以让整数自增的操作也变成原子性的
 *          private volatile int atomicInt = 0;
 *          amoticInt++;}
 *          </pre>
 *<p></p>
 * 本例结果：
 * <pre>{@code
 * Incrementing COUNTER to : 1
 * Got Change for COUNTER: 1
 * Incrementing COUNTER to : 2
 * Got Change for COUNTER: 2
 * Incrementing COUNTER to : 3
 * Got Change for COUNTER: 3
 * Incrementing COUNTER to : 4
 * Got Change for COUNTER: 4
 * Incrementing COUNTER to : 5
 * Got Change for COUNTER: 5}</pre>
 * 如果去掉关键字volatile {@link VolatileTest2}
 * @see VolatileTest2
 * @author kuifir
 * @date 2023/6/5 22:06
 */
public class VolatileTest {
    private static volatile int COUNTER = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int threadValue = COUNTER;
            while (threadValue < 5) {
                if (threadValue != COUNTER) {
                    System.out.println("Got Change for COUNTER: " + COUNTER + "");
                    threadValue = COUNTER;
                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {
            int threadValue = COUNTER;
            while (threadValue < 5) {
                System.out.println("Incrementing COUNTER to : " + (threadValue + 1) + "");
                COUNTER = ++threadValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
