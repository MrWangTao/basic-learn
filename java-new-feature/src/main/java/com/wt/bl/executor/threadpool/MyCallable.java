package com.wt.bl.executor.threadpool;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

/**
 * Callable和Runable接口的不同之处是有无返回值
 *
 * @author wt
 * @date 2018-12-03 15:13
 */
public class MyCallable implements Callable {

    private String name;

    public MyCallable(String name) {
        this.name = name;
    }

    @Override
    public Object call() throws Exception {
        System.out.println("this callable is: " + this.name);
        System.out.println("execute this ：" + this.name + " ON " + LocalDateTime.now().toString());
        return this.name;
    }
}
