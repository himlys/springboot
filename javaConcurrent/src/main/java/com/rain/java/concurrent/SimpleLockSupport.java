package com.rain.java.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleLockSupport {
    //    LockSupport比synchronized+wait notify关键字好的地方在于，他维持了一个顺序，保证unpark在park之前执行，也就是有一定的顺序性。
    public static void main(String[] args) {
//        simple();
//        lockSupport();
        lock();
    }

    public static void lockSupport() {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                LockSupport.park();
                System.out.println(sum);
            }
        });
        A.start();
        //睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LockSupport.unpark(A);
    }

    private static void lock() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        final Object obj = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                try {
                    lock.lock();
                    condition.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println(sum);
            }
        });
        A.start();
        //睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            lock.lock();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    private static void simple() {
        final Object obj = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                try {
                    synchronized (obj) {
                        obj.wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sum);
            }
        });
        A.start();
        //睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        synchronized (obj) {
            obj.notify();
        }
    }
}
