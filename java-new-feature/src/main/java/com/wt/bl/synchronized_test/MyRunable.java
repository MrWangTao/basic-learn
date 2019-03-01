package com.wt.bl.synchronized_test;

/**
 * @author wt
 * @date 2018-12-05 10:55
 */
public class MyRunable implements Runnable {

    private String name;

    public MyRunable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("current thread name: " + this.name + ", method run() has executed!");
        test();
    }

    public static synchronized void test() {
        try {
            Thread.sleep(1000);
            for (int i = 0; i < 5; i++) {
                // System.out.println("current thread name: " + this.name + ", this class's test() has executed:" + i);
                System.out.println("current thread name: " + Thread.currentThread().getName() + ", this class's test() has executed:" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fuck() {
        System.out.println("I am funcking little U bear");
    }

}
