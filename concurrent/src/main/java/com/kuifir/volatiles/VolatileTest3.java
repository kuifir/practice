package com.kuifir.volatiles;

/**
 * 不再让 ChangeListener 进行完全的忙等待，而是在 while 循环里面，小小地等待上 5 毫秒，看看会发生什么情况。
 * 输出结果：<pre>{@code
 * Incrementing COUNTER to : 1
 * Got Change for COUNTER: 1
 * Incrementing COUNTER to : 2
 * Got Change for COUNTER: 2
 * Incrementing COUNTER to : 3
 * Got Change for COUNTER: 3
 * Incrementing COUNTER to : 4
 * Got Change for COUNTER: 4
 * Incrementing COUNTER to : 5
 * Got Change for COUNTER: 5
 * }</pre>
 * 虽然我们的 COUNTER 变量，仍然没有设置 volatile 这个关键字，但是我们的 ChangeListener 似乎“睡醒了”。
 * 在通过 Thread.sleep(5) 在每个循环里“睡上“5 毫秒之后，ChangeListener 又能够正常取到 COUNTER 的值了。
 *
 * @author kuifir
 * @date 2023/6/5 22:06
 * @see VolatileTest2
 */
public class VolatileTest3 {
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
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
