package com.rain.java.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockFair implements Runnable{
    final Lock lock = new ReentrantLock(true);
    public void run(){
        while(true){
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t获得锁");
            }finally {
                lock.unlock();
            }
        }
    }
    public static void main(String args[]){
        ReentrantLockFair fair = new ReentrantLockFair();
        Thread t1 = new Thread(fair);
        Thread t2 = new Thread(fair);
        t1.start();
        t2.start();
    }
}
