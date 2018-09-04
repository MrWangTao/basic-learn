package com.wt.bl.enums;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangTao
 *         Created at 18/9/3 下午7:00.
 */
public interface ValuableEnum {

    Integer getValue();

    Map<Class, Map<Integer, Enum>> CACHE = new HashMap<>();

    static <E extends Enum<E> & ValuableEnum> E getEnum(Class<E> clazz, int rawValue) {
        E e = null;
        try {
            Map<Integer, Enum> cache = CACHE.get(clazz);
            if (cache == null) {
                cache = new HashMap<>();
                CACHE.put(clazz, cache);
            }

            e = (E) cache.get(rawValue);
            if (e == null) {
                e = Arrays.stream((E[]) MethodUtils.invokeStaticMethod(clazz, "values"))
                        .filter(v -> v.getValue() == rawValue).findFirst().get();
                cache.put(rawValue, e);
            }
            return e;

        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } finally {
            return e;
        }
    }


}
