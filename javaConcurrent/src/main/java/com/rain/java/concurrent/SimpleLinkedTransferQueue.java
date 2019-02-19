package com.rain.java.concurrent;

import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class SimpleLinkedTransferQueue {
    public static void main(String[] args) {
        TransferQueue<String> queue = new LinkedTransferQueue<String>();
        Thread producer = new Thread(new Producer(queue));
        producer.setDaemon(true); //设置为守护进程使得线程执行结束后程序自动结束运行
        for (int i = 0; i < 10; i++) {
            Thread consumer = new Thread(new Consumer(queue));
            consumer.setDaemon(true);
            consumer.start();
            try {
                // 消费者进程休眠一秒钟，以便以便生产者获得CPU，从而生产产品
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.start();
//        在这个地方让主线程等待了一下，主要想证实下，可以有很多个消费者一起等待。
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Consumer implements Runnable {
    private final TransferQueue<String> queue;

    public Consumer(TransferQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println(" Consumer " + Thread.currentThread().getName() + queue.poll());// 相当于不等待，直接返回null
//        try {
//            System.out.println(" Consumer " + Thread.currentThread().getName() + queue.take()); // 无线等待，poll等待结束后会返回null
//            System.out.println(" Consumer " + Thread.currentThread().getName() + queue.poll(1,TimeUnit.SECONDS));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

class Producer implements Runnable {
    private final TransferQueue<String> queue;

    public Producer(TransferQueue<String> queue) {
        this.queue = queue;
    }

    private String produce() {
        return " your lucky number " + (new Random().nextInt(100));
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(queue.hasWaitingConsumer());
                if (queue.hasWaitingConsumer()) {
//                    queue.transfer(produce());
                    queue.add(produce());
                }
                TimeUnit.SECONDS.sleep(1);//生产者睡眠一秒钟,这样可以看出程序的执行过程
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
