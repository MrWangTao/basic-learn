package com.wt.bl.basic;

import com.wt.bl.basic.abstracts.AbstractClass;

/**
 * @author wt
 * @date 2019-03-04 10:36
 */
public class AbstractExtendsClass extends AbstractClass {

    @Override
    public void callback() {
        System.out.println("callback");
    }

    @Override
    protected void aihei() {
        System.out.println("aihei");
    }
}
