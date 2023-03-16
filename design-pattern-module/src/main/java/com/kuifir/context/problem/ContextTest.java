package com.kuifir.context.problem;

import lombok.Data;

import java.util.stream.IntStream;

/**
 * 在执行一个比较长的请求任务时，这个请求可能会经历很多层的方法调用，
 * 假设我们需要将最开始的方法的中间结果传递到末尾的方法中进行计算，
 * 一个简单的实现方式就是在每个函数中新增这个中间结果的参数，
 * 依次传递下去
 *
 * <p/>
 * 然而这种方式太笨拙了，每次调用方法时，都需要传入 Context 作为参数，而且影响一些中间公共方法的封装。
 */
public class ContextTest {

    /**
     * 上下文类
     */
    @Data
    public class Context {
        private String name;
        private long id;
    }

    /**
     * 设置上下文名字
     */
    public class QueryNameAction {
        public void execute(Context context) {
            try {
                Thread.sleep(1000L);
                context.setName(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 设置上下文id
     */
    public class QueryIdAction {
        public void execute(Context context) {
            try {
                Thread.sleep(1000L);
                context.setId(Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 执行方法
     */
    public class ExecutionTask implements Runnable {

        private QueryNameAction queryNameAction = new QueryNameAction();
        private QueryIdAction queryIdAction = new QueryIdAction();

        @Override
        public void run() {
            final Context context = new Context();
            queryNameAction.execute(context);
            System.out.println("The name query successful");
            queryIdAction.execute(context);
            System.out.println("The id query successful");

            System.out.println("The name is " + context.getName() + "and id is " + context.getId());
        }
    }

    public static void main(String[] args) {
        IntStream.range(1,5)
                .peek(System.out::print)
                .forEach(i-> new Thread(new ContextTest().new ExecutionTask()).start());
    }
}
