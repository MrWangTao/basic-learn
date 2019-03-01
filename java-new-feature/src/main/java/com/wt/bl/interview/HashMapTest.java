package com.wt.bl.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wt
 * @date 2019-02-26 18:27
 */
public class HashMapTest {

    public static void hashMap() {
        Map<String, String> map = new HashMap();
        for (int i = 0; i < 13; i++) {
            map.put("key" + i, "value" + i);
        }
    }

    public static void main(String[] args) {
        hashMap();
    }

}
