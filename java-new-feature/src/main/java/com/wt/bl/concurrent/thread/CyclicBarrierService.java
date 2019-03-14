package com.wt.bl.concurrent.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author wt
 * @date 2019-03-13 14:26
 */
public class CyclicBarrierService implements Runnable {

    private CyclicBarrier cb;

    public CyclicBarrierService(CyclicBarrier cb) {
        this.cb = cb;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("进行等待的线程：" + Thread.currentThread().getName());
                cb.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
