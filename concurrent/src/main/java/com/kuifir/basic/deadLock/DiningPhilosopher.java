package com.kuifir.basic.deadLock;

import com.kuifir.basic.task.Nap;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * 出现死锁的四个原因：可对比{@link com.kuifir.deadLock.probem.DeadLockProblem}
 * 1. 互斥。这些任务使用的至少一项资源必须不是共享的。此处，一根筷子同时只能被一位Philosopher使用。
 * 2. 至少一个任务必须持有一项资源，并且等待正被另一个任务持有的资源。也就是说，如果要出现死锁，一位Philosopher必须持有一根筷子，并且正在等待另一根。
 * 3. 不能从一个任务中抢走一项资源。任务只能以正常的事件释放资源。
 * 4. 会发生循环等待，其中一个任务等待另一个任务持有的资源，另一个任务又在等待另一个任务持有的资源，
 * 以此类推，知道某个任务正在等待第一个任务持有的资源，由此一切都陷入了死循环。
 * @author kuifir
 * @date 2023/6/28 22:30
 */
public class DiningPhilosopher {
    private StickHolder[] sticks;
    private Philosopher[] philosophers;

    public DiningPhilosopher(int n) {
        sticks = new StickHolder[n];
        Arrays.setAll(sticks, i -> new StickHolder());
        philosophers = new Philosopher[n];
        Arrays.setAll(philosophers, i -> new Philosopher(i, sticks[i], sticks[(i + 1) % n]));
        // 通过颠倒筷子的顺序来修正死锁：
//        philosophers[1] = new Philosopher(0, sticks[0], sticks[1]);
        Arrays.stream(philosophers)
                .forEach(CompletableFuture::runAsync);
    }

    public static void main(String[] args) {
        // 立刻返回
        new  DiningPhilosopher(3);
        // 保持main不退出
        new Nap(10);
    }

}
