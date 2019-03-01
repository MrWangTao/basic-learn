package com.wt.bl.synchronized_test;

/**
 * @author wt
 * @date 2018-12-05 11:26
 */
public class MyThread{

    public synchronized void test() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("synchronized method test() : " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fuck() {
        try {
            Thread.sleep(1500);
            System.out.println("fuck you!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
