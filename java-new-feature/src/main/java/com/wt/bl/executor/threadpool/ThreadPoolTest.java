package com.wt.bl.executor.threadpool;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

/**
 * jdk1.5 jut包下 Executors
 */
public class ThreadPoolTest {

    private static void newCachedThreadPoolTest() {
        // 新建可缓存的线程池，如果有空闲线程直接使用，没有的话，新建
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Future<?> returnFuture = newCachedThreadPool.submit(new MyRunable("returnFuture"));
        System.out.println("当前任务是否已经完成：" + returnFuture.isDone());
        System.out.println("当前线程是否被取消：" + returnFuture.isCancelled());
        returnFuture.cancel(true);
        System.out.println("当前线程是否被取消：" + returnFuture.isCancelled());
        // newCachedThreadPool.shutdown(); // 指定完成之后关闭当前线程池
        /* List<Runnable> runnables = newCachedThreadPool.shutdownNow();
        runnables.stream().forEach(runnable -> System.out.println(((MyRunable)runnable).getRunableName()));*/
    }

    private static void newFixedThreadPoolTest() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 11; i++) {
            newFixedThreadPool.execute(new MyRunable("name_" + i));
        }
        List<Runnable> runnables = newFixedThreadPool.shutdownNow();
        runnables.stream().forEach(runnable -> System.out.println(((MyRunable) runnable).getRunableName()));
    }

    private static void newScheduledThreadPoolTest() {
        ExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(10);
        ScheduledFuture fuck_little_u_bear = ((ScheduledExecutorService) newScheduledThreadPool).schedule(new MyCallable("fuck little U bear"), 1, TimeUnit.SECONDS);
        System.out.println("current date time:" + LocalDateTime.now());
        System.out.printf("fuck_little_u_bear:" + fuck_little_u_bear.toString());
        newScheduledThreadPool.shutdown();
    }

    /**
     * 不关闭线程池执行器，即可实现Spring中的定时任务功能
     */
    private static void scheduledThreadPoolExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new MyRunable(""), 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
//        newCachedThreadPoolTest();
//        newFixedThreadPoolTest();
//        newScheduledThreadPoolTest();
        scheduledThreadPoolExecutor();
//        System.out.println(0 & ~1);
//        System.out.println("0: " + toBinary(0));
//        System.out.println("1: " + toBinary(1));
        // 00000000
        // 00000001
//        System.out.println(0 & 1);
//        System.out.println(1/2);
//        String s = Integer.toBinaryString(128);
//        System.out.println(s);
    }

    private static String toBinary(int num) {
        String str = "";
        if (num == 0) {
            return "00000000";
        }
        while (num != 0) {
            str = num % 2 + str;
            num = num / 2;
        }
        return str;
    }

}
