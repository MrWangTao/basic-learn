package com.wt.bl.java8.items;

/**
 * Java中的移位运算
 *
 * @author WangTao
 *         Created at 18/10/11 下午4:53.
 */
public class ShiftOperation {

    public static void main(String[] args) {
        /**
         * 1:左移
         * x << n, 结果为：x 乘以 2 的 n 次方
         */
        System.out.println(1 << 1);
        System.out.println(1 << 3);
        System.out.println(2 << 2);
        /**
         * 2:右移
         * x >> n, 结果为：x 除以 2 的 n 次方
         */
        System.out.println(8 >> 1);
        System.out.println(8 >> 2);
        System.out.println(-16 >> 20);

        /**
         * 3:无符号右移
         * 无符号右移，忽略符号位，空位都以0补齐
         */
        System.out.println(8 >>> 1);
        System.out.println(8 >>> 2);
        System.out.println(-16 >>> 20);

    }

}
