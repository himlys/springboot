package com.rain.java.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreDemo {
//    Semaphore大概就是个看门人
//    比如，我有一个宾馆，有十间房子，来了十一个人，这时前十个可以进去，第十一个过去的时候，没有房间了，陷入等待，超时异常（也可以永久等待）
//    这个fair参数，true的话，大概就是先到先得，false的话，可能会随机给一个人，不过这个是非常不严谨的，如果要保证顺序的话，用true不一定能获得想要的效果。
    private static final Semaphore semaphore = new Semaphore(3,true);
    //    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4, new ThreadFactory() {
        private AtomicInteger threadIndex = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "myTestThread" + threadIndex.getAndIncrement());
        }
    });

    private static class User {
        private final String name;
        private final int age;

        private User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    private static class InformationThread extends Thread {
        private final long timeoutMills = 1000;
        private final User user;

        private InformationThread(User user) {
            this.user = user;
        }

        public void run() {
            try {
                boolean acquired = semaphore.tryAcquire(timeoutMills, TimeUnit.SECONDS);
                if (acquired) {
                    String s = Thread.currentThread().getName() + " 大家好，我是" + user.getName() + ",今年" + user.getAge() + "岁";
                    System.out.println(s);
                    Thread.sleep(2000);
                    System.out.println("我将释放当前许可，时间为" + System.currentTimeMillis());
                    System.out.println("当前可用许可数量为：" + semaphore.availablePermits());
                    semaphore.release();
                } else {
                    String s = Thread.currentThread().getName() + " 大家好，我是" + user.getName() + ",今年" + user.getAge() + "岁";
                    throw new RuntimeException("not enough resource  " + s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        executorService.execute(new InformationThread(new User("tom", 17)));
        executorService.execute(new InformationThread(new User("petter", 22)));
        executorService.execute(new InformationThread(new User("sam", 10)));
        executorService.execute(new InformationThread(new User("bread", 19)));
        executorService.execute(new InformationThread(new User("lily", 21)));
        executorService.execute(new InformationThread(new User("lucy", 27)));
        executorService.execute(new InformationThread(new User("bob", 39)));
        executorService.execute(new InformationThread(new User("nancy", 66)));
        executorService.shutdown();
    }
}
