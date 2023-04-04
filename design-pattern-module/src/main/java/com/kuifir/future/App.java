package com.kuifir.future;


public class App {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        TaskServiceImpl<String, String> taskService = new TaskServiceImpl<>();//创建任务提交类
        MakeCarTask<String, String> task = new MakeCarTask<>();//创建任务

        Future<?> future = taskService.submit(task, "car1");//提交任务
        String result = (String) future.get();//获取结果

        System.out.print(result);
    }


    private static class MakeCarTask<T, P> implements Task<T, P> {

        @SuppressWarnings("unchecked")
        @Override
        public T doTask(P param) {

            String car = param + " is created success";

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return (T) car;
        }
    }
}
