package com.rain.java.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Setup {
    public static void main(String args[]) {
        BlockingQueue q = new ArrayBlockingQueue(20);
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }

    static class Producer implements Runnable {
        private final BlockingQueue queue;

        Producer(BlockingQueue q) {
            queue = q;
        }

        public void run() {
            try {
                int i = 0;
                while (true) {
                    queue.put(produce(i++));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        Object produce(int i) {
            return String.valueOf(i);
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue queue;
        Object current = null;

        Consumer(BlockingQueue q) {
            queue = q;
        }

        public void run() {
            try {
                while (true) {
                    current = queue.take();
                    consume(current);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        void consume(Object x) {
            System.out.println(Thread.currentThread().getName() + "\tconsumer " + x);
        }
    }
}
