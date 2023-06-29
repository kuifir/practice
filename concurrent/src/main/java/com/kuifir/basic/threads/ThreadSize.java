package com.kuifir.basic.threads;

import com.kuifir.basic.task.Nap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可以创建多少线程：
 * Thread对象中占据空间最多的部分是存储带执行方法的java栈。
 * 注意Thread对象的大小会随不同的操作系统的而不同。
 * 下面代码测试了这一点，他会不断地创建Thread对象，直到JVM内存耗尽；
 * @author kuifir
 * @date 2023/6/29 22:41
 */
public class ThreadSize {
    static class Dummy extends Thread {
        @Override
        public void run() {
            new Nap(1);
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        int count = 0;
        try {
            while (true) {
                exec.execute(new Dummy());
                count++;
                System.out.println(count);
            }
        } catch (Error e) {
            System.out.println(e.getClass().getSimpleName() + "：" + count);
            System.exit(0);
        } finally {
            exec.shutdown();
        }
    }
}
