package com.wt.bl.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangTao
 *         Created at 18/9/3 下午1:17.
 */
public enum StatusEnum implements ValuableEnum {

    ENABLED(1, "启动"),
    DISABLED(2, "删除");

    /**
     * Enum默认是取name作为枚举值key
     * 这里的 @JsonValue 表示我们要以这个字段作为枚举值的key
     */
    @JsonValue
    @Getter
    private Integer value;
    private String description;

    StatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * @description 缓存
     *
     * @param
     * @author wangtao
     * @return 
     * @date 18/9/3
     * @time 下午4:14
     **/
    /*public static Map<Integer, StatusEnum> statusEnumMap = new HashMap<Integer, StatusEnum>();
    static {
        StatusEnum[] values = StatusEnum.values();
        Arrays.stream(values).forEach(statusEnum -> statusEnumMap.put(statusEnum.value, statusEnum));
        System.out.println(statusEnumMap.toString());
    }*/

    /**
     * @description 根据类型获取StatusEnum值
     *
     * @param value
     * @author wangtao
     * @return 
     * @date 18/9/3
     * @time 下午1:33
     **/
    /*public static StatusEnum getStatusEnum(Integer value) {
        return statusEnumMap.get(value);
    }*/

}
