package com.rain.java.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;

public class MyLinkedBlockingDeque {
    LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque(20);
    static ThreadFactory threadFactory = new SimpleBlockingQueue.InnerThreadFactory();
    Runnable active;
    Executor executor = new DirectExecutor();

    public boolean add(Runnable runnable) {
        boolean q = false;
//        一般情况下，这种链表的容量没有太大必要，但是如果使用add or offer()的时候还是会触发Deque full的异常。特殊处理的时候可能用得到
//        大部分情况下，我们可以使用put，也就是判定condition（notFull）等待，这个是无限等待的，如果出现死锁的时候很麻烦，使用offer(object,time,timeunit)
//        的方式比较好。
        try {
            linkedBlockingDeque.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return q;
    }

    protected void scheduleNext() {
        active = linkedBlockingDeque.poll();
        if (active != null) {
            executor.execute(active);
        }
    }

    public void execute(Runnable command) {
        linkedBlockingDeque.offer(command);
        if (active == null) {
            scheduleNext();
        }
    }

    public static void main(String args[]) {
        MyLinkedBlockingDeque myLinkedBlockingDeque = new MyLinkedBlockingDeque();
        for (int i = 0; i < 10000; i++) {
            final int out = i;
            new Thread(() -> {
                myLinkedBlockingDeque.add(threadFactory.newThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + "\t" + out);
                        myLinkedBlockingDeque.scheduleNext();
                    }
                }));
            }).start();
        }
        new Thread(() -> {
            myLinkedBlockingDeque.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + 12345);
                myLinkedBlockingDeque.scheduleNext();
            });
        }).start();
    }
}
