package com.wt.bl.basic;

/**
 * @author wt
 * @date 2019-03-07 17:36
 */
public class Child extends Parent {

    public String name;

    public Child() {
        super();
        this.name = "Child";
    }

    public void hello(String param) {
        System.out.println("hello Child" + param);
    }

    public void hi(Object param) {
        System.out.println("hi Child" + param);
    }

    public void laugh() {
        System.out.println("I am very happy");
    }

    /**
     * @link https://blog.csdn.net/fan2012huan/article/details/50999777
     * @link http://blog.sina.com.cn/s/blog_3fcdcdfc0102wrkk.html
     * 方法重载也会进行静态调用，看静态类型
     * 方法重写： 看实际类型，实际类型如果实现了该方法则直接调用该方法，如果没有实现，则在继承关系中从低到高搜索有无实现。
     * Java的方法调用过程：
     * 1. 编译器查看对象的声明类型和方法名（对象变量的声明类型），通过声明类型找到方法列表。
     * 2. 编译器查看调用方法时提供的参数类型。
     * 3. 如果方法是private、static、final或者构造器，编译器就可以确定调用那个方法。这是静态绑定。
     * 4. 如果不是上述情况，就要使用运行时（动态）绑定。在程序运行时，采用动态绑定意味着：虚拟机将调用对象实际类型所限定的方法。
     * 动态绑定的过程：
     * 1. 虚拟机提取对象的实际类型的方法表；
     * 2. 虚拟机搜索方法签名；
     * 3. 调用方法
     *
     * @param args
     */
    public static void main(String[] args) {
        Child cp4 = new Child();
        System.out.println(cp4.name);
        cp4.hello(cp4.name);
        cp4.hi(cp4.name);
        Parent cp3 = cp4;
        System.out.println(cp3.name);
        cp3.hello(cp3.name);
        cp3.hi(cp3.name);
    }

}
