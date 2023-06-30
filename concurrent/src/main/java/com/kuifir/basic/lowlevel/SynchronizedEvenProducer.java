package com.kuifir.basic.lowlevel;

import com.kuifir.basic.task.Nap;

/**
 * 将{@link EvenProducer}的next()方法修改为synchronized版本的IntGenerator，
 * 这样可以阻止不符合预期的线程访问
 *
 * @author kuifir
 * @date 2023/6/30 22:57
 */
public class SynchronizedEvenProducer extends IntGenerator {

    private int currentEvenValue = 0;

    @Override
    public synchronized int next() {
        ++currentEvenValue;
        // 可以导致更快失败,但由于互斥锁在临界区一次阻止了多个任务，因此这不会导致失败发生。
        new Nap(0.1);
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new SynchronizedEvenProducer());
    }
}
