package com.wt.bl.executor.threadpool;

import lombok.Getter;

/**
 * @author wt
 * @date 2018-12-03 14:31
 */
public class MyRunable implements Runnable {

    @Getter
    private String runableName;

    public MyRunable(String runableName) {
        this.runableName = runableName;
    }

    @Override
    public void run() {
        System.out.println("this runable name is: " + this.runableName);
        /*try {
            Thread.sleep(1000);
            System.out.println("this runable name is: " + this.runableName);
        } catch (InterruptedException e) {
            System.out.println(this.runableName + " was Interrupted");
        }
*/
    }
}
