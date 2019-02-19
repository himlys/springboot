package com.rain.java.concurrent;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SerialExecutor implements Executor {
    final Queue<Runnable> tasks = new ArrayDeque<>();
    final Lock lock = new ReentrantLock();
//    当fair=true的时候，先尝试获取锁的线程先获取锁，false为随机
    final Lock executeLock = new ReentrantLock(false);
    final Executor executor = new DirectExecutor();
    Runnable active;

    @Override
    public void execute(Runnable command) {
        lock.lock();
        try {
            tasks.offer(new Runnable() {
                @Override
                public void run() {
                    command.run();
                }
            });
        } finally {
            lock.unlock();
        }
        if (active == null) {
            scheduleNext();
        }
    }

    public boolean add(Runnable command) {
        System.out.println("锁状态 = " + Thread.currentThread().getName() + "\t" + lock.toString());
        lock.lock();
        System.out.println("加锁 = " + Thread.currentThread().getName() + "\t" + lock.toString());
        boolean result = false;
        try {
            result = tasks.add(() -> {
                command.run();
            });
        } finally {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
            System.out.println("释放锁 = " + Thread.currentThread().getName() + "\t" + lock.toString());
        }
        return result;
    }

    protected void scheduleNext() {
        executeLock.lock();
        try {
            active = tasks.poll();
        } finally {
            executeLock.unlock();
        }
        if (active != null) {
            executor.execute(active);
        }
    }

    public static void main(String args[]) {
        SerialExecutor e = new SerialExecutor();
        for(int i = 0 ; i < 100; i++){
            final int out = i;
            new Thread(()->{
                e.add(()-> {
                    System.out.println(out);
                    e.scheduleNext();
                });
            }).start();
        }
        new Thread(()->{
            e.execute(()-> {
                System.out.println(12345);
                e.scheduleNext();
            });
        }).start();
    }
}
