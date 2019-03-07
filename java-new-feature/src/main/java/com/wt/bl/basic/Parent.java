package com.wt.bl.basic;

/**
 * @author wt
 * @date 2019-03-07 17:35
 */
public class Parent {

    public String name;

    public void hello(String param) {
        System.out.println("hello parent!" + param);
    }

    public void hi(String param) {
        System.out.println("hi parent!" + param);
    }

    public Parent() {
        this.name = "Parent";
    }

}
