package com.kuifir.normal.cleanup;

import java.util.concurrent.TimeUnit;

/**
 * 终止条件
 * 使用finalize()方法来检查对象是否被正确清理。
 * <p/>
 * finalize()有一个有趣的用法，它不依赖每次都被调用。这就是对象终止条件的验证。
 * 当你不再使用某个对象是，也就是当它可以被清理时，该对象应该处于可以安全释放其内存的状态。
 * 例如，如果对象代表一个打开的文件，那么在对象被垃圾收集之前，程序员应该关闭该文件。
 * 如果对象的任何部分没有被正确清理，程序中就有了一个很难被发现的错误。
 * finalize()方法最终能发现这个问题，即使它并不总是被调用。
 * 如果其中一个终结操作正好揭示了这个错误，那你就会发现这个问题，这才是我们真正关心的。
 *
 * @author kuifir
 * @date 2023/5/13 23:56
 */
public class TerminationCondition {
    public static void main(String[] args) throws InterruptedException {
        Book novel = new Book(true);
        // 正确清理
        novel.checkIn();
        // 没有清理就丢掉了该对象的引用：
        new Book(true);
        // 强制垃圾收集和终结操作
        System.gc();
        TimeUnit.SECONDS.sleep(1);
    }
}

class Book {
    boolean checkout = false;

    /**
     * 终止条件：苏哦有的的Book对象必须在被垃圾收集之前签入（check in）
     * main方法里没有执行图书的嵌入操作。如果没有finalize()来验证终止条件，这里的错误很难被发现，
     * @param checkout
     */
    Book(boolean checkout) {
        this.checkout = checkout;
    }

    void checkIn() {
        checkout = false;
    }

    @Override
    protected void finalize() throws Throwable {

        try {
            if (checkout) {
                System.out.println("Error: checked out");
            }
        } finally {
            // 通常情况下也需要这么做：
            // 调用基类版本
            super.finalize();
        }

    }
}