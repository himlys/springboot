package com.rain.java.concurrent.cas;

import java.util.concurrent.*;

public class CustomFutureTaskThreadPoolExecutor<T> extends ThreadPoolExecutor {
//    ThreadPoolExecutor中的execute函数是其中的核心函数
//    在execute的时候，会判定线程池个数，如果线程池个数是10个，会创建十个Worker
//    每一个Worker是一个资源池，如果已达到最大线程池数量，将不会创建Worker对象，所有的任务（程序所添加的线程）都将添加到队列中，如果是链表队列，则会无限添加
//    如果是有限队列（数组ArrayBlockQueue，或者有界链表），到达最大值得时候会抛出异常。
//    Worker中有个getTask函数，从Queue中获取消息，其中使用的是take函数，意味着，这里将无线等待下去。所以，我们的程序使用PoolExecutor的时候要谨慎，防止不停的创建该对象。
//    除非作为一个公用的线程池，系统启动后一直使用，不然的话，尽量在使用完线程池的时候，关闭当前线程池。
//    建议规划好代码，避免重复创建线程池，尽量使用公用对象，也就是所有请求使用同一个ThreadPoolExecutor
//
//    submit函数，本质上还是调用execute函数，在调用之前做一个RunnableFuture对象，该对象主要是实现run函数，还有get函数
//    run函数调用目标程序，get函数获取结果对象，这里在获取的时候先判定目标程序是否执行完成，如果没完成，主线程进入等待，在目标线程完成之后
//    回调告诉主线程，返回结果。阻塞主线程主要使用的是unsafe.park，新JDK的很多地方都是用的这种处理方式。
    public CustomFutureTaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new CustomFutureTask<T>(callable);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new CustomFutureTask<T>(runnable,value);
    }
    public static void main(String args[]){
        CustomFutureTaskThreadPoolExecutor executor = new CustomFutureTaskThreadPoolExecutor(1,1,3,TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        Future<String> future = executor.submit(()->{
            System.out.println("abc");
            return "new abc";
        });
        String result = null;
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.submit(()->{
            System.out.println("xyz");
            return "new xyz";
        });
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.submit(()->{
            System.out.println("@#$");
            return "new @#$";
        });
        executor.shutdown();
    }
}
