package com.wt.bl.interview;

import java.math.BigDecimal;

/**
 * @author wt
 * @date 2019-02-26 17:47
 */
public class SplitTest {

    public static void splitByChar() {
        String s = "a|b|c|d";
        String s1 = "a.b.c.d";
        String[] split11 = s.split("\\|");
        String[] split12 = s1.split("\\.");
        System.out.println(split11.length + "===" + split12.length);// 结果为正常的4
        String[] split21 = s.split("|");
        String[] split22 = s.split(".");
        System.out.println(split21.length + "===" + split22.length); // 为什么结果会是：7 === 0
        System.out.println(s.split("").length);
        /**
         * 答案解释：
         * 如果用 "|" 作为分隔符的话，必须是如下写法： String.split("\\|")，这样才能正确的分隔开，不能使用String.split("|")
         * "." 和 "|"都是转义字符，必须得加"\\";这是利用了java中正则表达式的一个点
         * 在正则表达式中"|"是或的关系，在Java中类似是没有使用字符,也就是说s.split("|"); 等价于s.split("");
         * 在正则表达式中"."是表示任意字符，那么s.split("."); 就表示了我是任意字符都匹配上了，那么java就茫然了，所以范围为0
          */

    }

    public static void integerEqualsProblem() {
        Integer a = 127;
        Integer b = 127;
        int aa = 127;
        int bb = 127;
        Integer c = new Integer(127);
        Integer d = new Integer(127);
        System.out.println(a == b);
        System.out.println(a == aa);
        System.out.println(aa == bb);
        System.out.println(c == d);
        System.out.println(b == c);
    }

    public static void bigdecimalTest() {
        /**
         * 结果为什么是false？
         * 是因为如果我们给定的是浮点型，那么这个数字的位数可能还有位数，
         * 但是如果我们给定的是字符，那么位数就已经确定了。
         */
        BigDecimal x = new BigDecimal(0.1);
        BigDecimal y = new BigDecimal("0.2");
        BigDecimal z = new BigDecimal("0.3");
        BigDecimal a = z.subtract(y);
        System.out.println(a);
        boolean result = a.compareTo(x) == 0;
        System.out.println(result);
    }

    public static void main(String[] args) {
        splitByChar();
        integerEqualsProblem();
        bigdecimalTest();
    }

}
