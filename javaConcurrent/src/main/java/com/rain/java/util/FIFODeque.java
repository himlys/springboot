package com.rain.java.util;

import com.rain.java.concurrent.DirectExecutor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.Executor;

public class FIFODeque {
    final Queue<Runnable> arrayDeque = new ArrayDeque();
    final Executor executor = new DirectExecutor();
    Runnable active;

    public synchronized boolean add(Runnable command) {
        arrayDeque.add(() -> {
            command.run();
        });
        return false;
    }

    protected synchronized void scheduleNext() {
        active = arrayDeque.poll();
        if (active != null) {
            executor.execute(active);
        }
    }

    public synchronized void execute(Runnable command) {
        arrayDeque.offer(new Runnable() {
            @Override
            public void run() {
                command.run();
            }
        });
        if (active == null) {
            scheduleNext();
        }
    }

    public static void main(String args[]) {
        FIFODeque e = new FIFODeque();
        for (int i = 0; i < 100; i++) {
            final int out = i;
            e.add(() -> {
                System.out.println(out);
                e.scheduleNext();
            });
        }
        e.execute(() -> {
            System.out.println(12345);
            e.scheduleNext();
        });
    }
}
