package com.kuifir.juc.completionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * {@link CompletionService} 的实现原理是内部维护了一个阻塞队列，当任务执行结束就把任务的执行结果加入到阻塞队列中，
 * 不同的是 CompletionService 是把任务执行结果的 Future 对象加入到阻塞队列中。
 *
 * @see java.util.concurrent.CompletionService
 */
public class CompletionServiceDemo {
    public static void main(String[] args) {
        System.out.println(useDemo());
    }

    /**
     * 利用 CompletionService 实现 Dubbo 中的 Forking Cluster
     * 支持并行地调用多个查询服务，只要有一个成功返回结果，整个服务就可以返回
     * @return
     */
    public static Integer useDemo() {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs =
                new ExecutorCompletionService<>(executor);
        // 用于保存Future对象
        List<Future<Integer>> futures =
                new ArrayList<>(3);
        //提交异步任务，并保存future到futures
        futures.add(cs.submit(() -> {
            sleep(10);
            return 1;
        }));
        futures.add(
                cs.submit(() -> {
                    sleep(3);
                    return 2;
                }));
        futures.add(
                cs.submit(() -> {
                    sleep(4);
                    return 3;
                }));
        // 获取最快返回的任务执行结果
        Integer r = 0;
        try {
            // 只要有一个成功返回，则break
            for (int i = 0; i < 3; ++i) {
                r = cs.take().get();
                //简单地通过判空来检查是否成功返回
                if (r != null) {
                    break;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //取消所有任务
            for (Future<Integer> f : futures)
                f.cancel(true);
        }
        // 返回结果
        return r;
    }

    private static void sleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
