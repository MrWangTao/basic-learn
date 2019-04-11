package com.wt.bl.classloader;

/**
 * @author wt
 * @date 2019-03-26 10:12
 */
public class LoadOrder {

    private static  LoadOrder loadOrder = new LoadOrder();
    public static int counter1;
    public static int counter2 = 0;

    private LoadOrder () {
        counter1 ++;
        counter2 ++;
    }

    public static LoadOrder getInstance() {
        return loadOrder;
    }

    /**
     * 在类加载的准备阶段，会将类变量加载到方法区中，并赋予初始值，注意不是我们java代码中给定的值
     * 在来加载的解析阶段，会按照类变量的顺序加载到构造器中去执行，按照例子中解释如下
     * 准备阶段  counter1 、 counter2 为0
     * 在解析阶段，类变量和静态块中的信息会在构造器中按照顺序执行
     *
     * @param args
     */
    public static void main(String[] args) {
        LoadOrder instance = getInstance();
        System.out.println(instance.counter1); // 1
        System.out.println(instance.counter2); // 0
    }

}
