package com.rain.java.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleThreadPoolExecutor {
    //    这个就比较自有奔放了，可以随意开线程，但缺点就是他会存在线程池资源不足的情况，也就是我已经跑了20个线程，又来一个，直接会抛异常。没有等待机制。
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 20, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), (run) -> {
        return new Thread(run);
    }, ((r, executor1) -> {
        System.out.println("do nothing");
    }));
    //    如果存在的话，会使用已经缓存的线程，默认缓存60秒，但是有个问题，就是它会不停的创建线程，如果线程没有回收，最后就是java.lang.OutOfMemoryError: unable to create new native thread
//    使用这个的话，稍微考虑下这个，不过一般不会创建那么多线程的，除非死锁。
//    这里使用的是SynchronousQueue，他没有容器，当有消费者加入的时候，他将生产者的消息push出去。
    static ExecutorService cachedExecutor = Executors.newCachedThreadPool();
    //    和上面的区别没有特别大，主要是不会缓存线程，还有就是他永远不会开新线程，就这么一个，所以也没有上面那个资源不足的情况。
//    这里是用的是LinkedBlockingQueue，链表无限大，所以他支撑无限多的线程（除非大到内存溢出，非常难）
    static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
    //    和上面的区别就是可以多个，也没有资源不足的情况，他会等待，不会开新的。
//    队列是同上的，所以他也是无限多个。
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10, (r) -> {
        return new Thread(r);
    });

    //    产生异常的方式主要是因为线程满了，将线程加入到队列中，队列如果也满了，会直接异常出来，没有等待机制。所以我们可以使用LinkedBlockingQueue来避免这种情况的出现
//    但缺点就是万一死锁，就OutOfMemoryError了。

    //    JDK示例中的，在某一条件下pause或者resume
    static PausableThreadPoolExecutor pausableThreadPoolExecutor = new PausableThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public static void main(String args[]) {
        for (int i = 0; ; i++) {
            final int a = i;
//            executor.execute(() -> {
//                doSomething(a);
//            });
//            cachedExecutor.execute(()->{
//                doSomething(a);
//            });
//            singleExecutor.execute(()->{
//                doSomething(a);
//            });
//            fixedThreadPool.execute(()->{
//                doSomething(a);
//            });

//            pausableThreadPoolExecutor.execute(()->{
//                doSomething(a);
//            });
//            if(a == 100){
//                pausableThreadPoolExecutor.pause();
//            }
//            System.out.println(pausableThreadPoolExecutor.getQueue().size());
//            if(a == 1000){
//                pausableThreadPoolExecutor.resume();
//            }
//            if(a > 1100){
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                pausableThreadPoolExecutor.pause();
//            }
//            submit Callable
//            Future future = singleExecutor.submit(()->{
//                submitDoSomething(a);
//                return "abc";
//            });
//            submit Runnable
            Future future = singleExecutor.submit(()->{
                submitDoSomething(a);
            },"bcd");
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            break;
        }
//        scheduledExecutorService.schedule(() -> {
//            doSomething(10);
//        }, 3, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleWithFixedDelay(() -> {
//            doSomething(20);
//        }, 10, 3, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            doSomething(50);
//        },3,System.currentTimeMillis() + 1000,TimeUnit.SECONDS);
    }

    private static void doSomething(int i) {
        System.out.println(Thread.currentThread().getName() + "\t" + "run" + "\t" + i);
    }
    private static void submitDoSomething(int i) {
        System.out.println(Thread.currentThread().getName() + "\t" + "submit" + "\t" + i);
    }
}

class PausableThreadPoolExecutor extends ThreadPoolExecutor {
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();

    public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) unpaused.await();
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }
}