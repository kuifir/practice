package com.kuifir.basic.deadLock;

/**
 * 每个Philosopher都是一个任务，它尝试从左右两边pickUp()筷子来吃饭，然后通过putDown()来释放这些筷子：
 * 两个Philosopher不可能成功地同时take()同一根筷子。
 * 另外，如果一根筷子已经被一个Philosopher拿走，下一个尝试拿走同一根筷子的Philosopher会阻塞，等该该筷子被释放。
 * 结果是一个看似没问题的程序死锁了。为了使语法更清晰，将集合改用数组来实现：{@link DiningPhilosopher}
 *
 * @author kuifir
 * @date 2023/6/28 22:17
 */
public class Philosopher implements Runnable {
    private final int seat;
    private final StickHolder left, right;

    public Philosopher(int seat, StickHolder left, StickHolder right) {
        this.seat = seat;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "P" + seat;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Thinking");
            right.pickUp();
            left.pickUp();
            System.out.println(this + "eating");
            right.putDown();
            left.putDown();
        }
    }
}
