package com.rain.java.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleBlockingQueue {
    final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10, true);
    Runnable active;
    static ThreadFactory threadFactory = new InnerThreadFactory();
    Executor executor = new DirectExecutor();

    public boolean add(Runnable runnable) {
        try {
            boolean q = false;
//            boolean q = queue.offer(runnable,5000, TimeUnit.MILLISECONDS);
//            if(!q){
//                System.out.println("q = " + q);
//            }
//            add 本质上是没有参数的offer，也就是不等待添加。
//            put 可能会出现死锁，慎用
            queue.put(runnable);
            return q;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

    }

    protected void scheduleNext() {
        active = queue.poll();
        if (active != null) {
            executor.execute(active);
        }
    }

    public void execute(Runnable command) {
        queue.offer(command);
        if (active == null) {
            scheduleNext();
        }
    }

    public static void main(String args[]) {
        SimpleBlockingQueue e = new SimpleBlockingQueue();

        for (int i = 0; i < 100; i++) {
            final int out = i;
            new Thread(() -> {
                e.add(threadFactory.newThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + "\t" + out);
                        e.scheduleNext();
                    }
                }));
            }).start();
        }
        new Thread(() -> {
            e.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + 12345);
                e.scheduleNext();
            });
        }).start();
    }

    static class InnerThreadFactory implements ThreadFactory {
        final ThreadGroup group = new ThreadGroup("testThreadGroup");
        final AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(group, r, "simple-blocking-queue" + atomicInteger.getAndIncrement());
        }
    }
}