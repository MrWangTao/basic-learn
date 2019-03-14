package com.wt.bl.concurrent.thread;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author wt
 * @date 2019-03-12 17:50
 */
public class Service {

    private CountDownLatch latch;

    private List<Integer> list;

    public Service(CountDownLatch latch, List<Integer> list) {
        this.latch = latch;
        this.list = list;
    }

    public void exec() {
         try {
             System.out.println(Thread.currentThread().getName() + " await");
             Thread.sleep(500);
             System.out.println(Thread.currentThread().getName() + " finished");
             int i = new Random().nextInt(10) + 1;
             System.out.println(i);
             list.add(i);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }finally {
             latch.countDown();
         }
    }

}
