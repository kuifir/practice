package com.kuifir.juc.future;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Future 接口有 5 个方法，
 * - 取消任务的方法 cancel()
 * - 判断任务是否已取消的方法 isCancelled()
 * - 判断任务是否已结束的方法 isDone()
 * - 以及2 个获得任务执行结果的 get() 和 get(timeout, unit)，
 * <pre>{@code
 * // 取消任务
 * boolean cancel(
 * boolean mayInterruptIfRunning);
 * // 判断任务是否已取消
 * boolean isCancelled();
 * // 判断任务是否已结束
 * boolean isDone();
 * // 获得任务执行结果
 * get();
 * // 获得任务执行结果，支持超时
 * get(long timeout, TimeUnit unit);}
 * </pre>
 */
public class FutureDemo {
    static Integer a = 3000;
    static Integer x = 4000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor
                = Executors.newFixedThreadPool(1);
        // 创建Result对象r
        Result r = new Result();
        r.setAAA(a);
        // 提交任务
        Future<Result> future =
                executor.submit(new Task(r), r);
        Result fr = future.get();
        // 下面等式成立
        System.out.println(fr == r);
        System.out.println(Objects.equals(fr.getAAA(), a));
        System.out.println(Objects.equals(fr.getXXX(), x));


    }

    static class Task implements Runnable {
        Result r;

        //通过构造函数传入result
        Task(Result r) {
            this.r = r;
        }

        public void run() {
            //可以操作result
            a = r.getAAA();
            r.setXXX(x);
        }
    }

    private static class Result {
        Integer AAA;
        Integer XXX;

        public Integer getAAA() {
            return AAA;
        }

        public void setAAA(Integer AAA) {
            this.AAA = AAA;
        }

        public Integer getXXX() {
            return XXX;
        }

        public void setXXX(Integer XXX) {
            this.XXX = XXX;
        }
    }
}
