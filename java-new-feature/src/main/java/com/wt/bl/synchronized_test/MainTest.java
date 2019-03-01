package com.wt.bl.synchronized_test;

/**
 * @author wt
 * @date 2018-12-05 10:56
 */
public class MainTest {

    public static void main(String[] args) {
        /**
         * 1: 验证Synchronized关键字修饰普通方法，获取的是当前对象的锁，注意：一个对象实例只有一个锁，每个对象实例都有一个锁
         * 2: 验证Synchronized关键字修饰静态方法，获取的是当前对象所属Class的锁，并且每个Class只有一个锁
         *
         * static关键字修饰的方法或者变量，是属于当前类的，而不是每个具体实例
         *
         * Synchronized关键字修饰的方法，待方法执行结束后会释放掉当前锁
         */
//        new Thread(new MyRunable("1"), "1").start();
//        new Thread(new MyRunable("2"), "2").start();
//        MyRunable myRunable = new MyRunable("3");
//        new Thread(myRunable, "1").start();
//        new Thread(myRunable, "2").start();
        /**
         * 1: 测试当Synchronized修饰的方法被调用是，同一个对象的非同步方法依旧可以调用
         */
        MyThread myThread = new MyThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                myThread.test();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                myThread.fuck();
            }
        }).start();


    }

}
