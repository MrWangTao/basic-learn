package com.wt.bl.concurrent;

import com.wt.bl.concurrent.thread.CyclicBarrierService;

import java.util.concurrent.CyclicBarrier;

/**
 * @author wt
 * @date 2019-03-13 14:20
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("exec it again");
            }
        });
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new CyclicBarrierService(cb));
            thread.start();
        }
    }

}
