package com.wt.bl.java8.streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangTao
 *         Created at 18/8/20 上午10:43.
 */
public class StreamApiTest {

    private static void streamMethodTest() {
        List list = Arrays.asList("a", "b", "ac");
        Object a = list.stream().filter(p -> p.equals("a"))
                .collect(Collectors.toList());
        System.out.println(a);
    }

    /**
     * @description 根据多个字段进行排序
     *
     * @param 
     * @author wangtao
     * @return 
     * @date 18/8/20
     * @time 下午1:39
     **/
    private static List orderByAgeThenName() {
        List<User> users = Arrays.asList(new User("张三", 20), new User("李四", 20),
                new User("王五", 21), new User("张七", 22), new User("开心", 25));
        /**
         * java8之后
         */
        List<User> orderByAgeThenName = users.stream()// .sorted(Comparator.comparing(User::getName).thenComparing(User::getName))
                .sorted(Comparator.comparing(User::getAge).thenComparing(User::getName))
                .collect(Collectors.toList());
        /**
         * java8之前 匿名内部类
         */
        List<User> collect = users.stream().sorted(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                Integer age1 = o1.getAge();
                Integer age2 = o2.getAge();
                int i = age1.compareTo(age2);
                if (i != 0) {
                    return i;
                }
                int j = o1.getName().compareTo(o2.getName());
                return j;
            }
        }).collect(Collectors.toList());
        return orderByAgeThenName;
    }

    public static void main(String[] args) {
//        streamMethodTest();
        orderByAgeThenName();
    }

}
