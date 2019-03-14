package com.wt.bl.concurrent;

import com.wt.bl.concurrent.thread.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p>作用：等待其他的线程都执行完任务，必要时对各个任务的执行结果进行汇总，然后主线程才继续往下执行</p>
 * <p>主要有两个方法：countDown() 和 await()</p>
 * countDown()用于使计数器减1，一般是执行任务的线程调用
 * await() 使调用该方法的线程处于等待状态，一般是主线程调用
 *
 * <p>CountDownLatch 不能循环使用，也就是说不能倒退前进倒退前进</p>
 * @author wt
 * @date 2019-03-12 17:41
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        List<Integer> list = new ArrayList<>();
        Service service = new Service(latch, list);
        // 可以学习下这个写法
        Runnable runnable = () -> service.exec();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        /**
         * latch.await() 表示当前执行的线程处于等待（可以理解为阻塞），直到CountDownLatch 为0
         */
        latch.await();
        doSomething(list);

    }

    public static void doSomething(List<Integer> list) {
        Integer reduce = list.stream().reduce(0, Integer::sum);
        System.out.println("所有线程执行完成，进行合并操作结果为:" + reduce);
    }

}
