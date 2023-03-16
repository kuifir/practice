package com.kuifir.context.solve;

import lombok.Data;

import java.util.stream.IntStream;

public class ContextTest {
    @Data
    public static class Context {
        private String name;
        private long id;
    }

    /**
     * 复制上下文到ThreadLocal中
     */
    public static final class ActionContext {
        ThreadLocal<Context> threadLocal = new ThreadLocal<Context>() {
            @Override
            protected Context initialValue() {
                return new Context();
            }
        };

        public static ActionContext getActionContext() {
            return ContextHolder.actionContext;
        }

        public Context getContext() {
            return threadLocal.get();
        }

        public static class ContextHolder {
            private final static ActionContext actionContext = new ActionContext();
        }

        private ActionContext() {
        }
    }

    /**
     * 设置上下文名称
     */
    public class QueryNameAction {
        public void execute() {
            try {
                Thread.sleep(1000L);
                ActionContext.getActionContext().getContext().setName(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 设置上下文id
     */

    public class QueryIdAction {
        public void execute() {
            try {
                Thread.sleep(1000L);
                ActionContext.getActionContext().getContext().setId(Thread.currentThread().getId());
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
            ActionContext actionContext = new ActionContext();
            queryNameAction.execute();
            System.out.println("The name query successful");
            queryIdAction.execute();//设置线程名
            System.out.println("The id query successful");

            System.out.println("The Name is " + ActionContext.getActionContext().getContext().getName()
                    + " and id " + ActionContext.getActionContext().getContext().getId());

        }
    }

    public static void main(String[] args) {
        IntStream.range(1, 5).forEach(i -> new Thread(new ContextTest().new ExecutionTask()).start());
    }
}
