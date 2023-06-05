package com.kuifir.volatiles;

/**
 * 在{@link VolatileTest}的基础上去掉COUNTER的关键字volatile,之后输出结果:
 * <pre>{@code
 * Incrementing COUNTER to : 1
 * Incrementing COUNTER to : 2
 * Incrementing COUNTER to : 3
 * Incrementing COUNTER to : 4
 * Incrementing COUNTER to : 5
 * }</pre>
 * 你会发现，{@link ChangeMaker}还是能正常工作的，但是 {@link ChangeListener}不再工作了。
 * 在 ChangeListener 眼里，它似乎一直觉得 COUNTER 的值还是一开始的 0。
 * 似乎 COUNTER 的变化，对于我们的 ChangeListener 彻底“隐身”了。
 * <p></p>
 * 可以再对程序做一些小小的修改。我们不再让 ChangeListener 进行完全的忙等待，
 * 而是在 while 循环里面，小小地等待上 5 毫秒，看看会发生什么情况。{@link VolatileTest3}
 *
 * @author kuifir
 * @date 2023/6/5 22:06
 * @see VolatileTest3
 * @see VolatileTest
 */
public class VolatileTest2 {
    private static int COUNTER = 0;

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
