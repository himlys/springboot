package com.rain.java.concurrent;

import java.util.concurrent.Executor;

public class DirectExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        if(command instanceof Thread){
            ((Thread) command).start();
        }else{
            new Thread(command,"start-thread").start();
        }
    }

    public static void main(String args[]) {
        new DirectExecutor().execute(() -> System.out.println(321));
    }
}
