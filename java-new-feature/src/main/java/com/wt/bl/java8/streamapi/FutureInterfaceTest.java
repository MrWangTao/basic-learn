package com.wt.bl.java8.streamapi;

import java.util.concurrent.*;

/**
 * java5中引入的Future接口的测试
 * @author WangTao
 *         Created at 18/8/20 下午1:56.
 */
public class FutureInterfaceTest {

    private static void test1() {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                Thread.sleep(1000);
                return 2.0;
            }
        });
        try {
            Thread.sleep(1100);
            System.out.println("after sleep 1100s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // 判断任务是否已经执行完成
            boolean done = future.isDone();
            Double aDouble = future.get(1, TimeUnit.SECONDS);
            System.out.println(aDouble);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test1();
    }

}
