package com.wt.bl.enums.formatter;

import com.wt.bl.enums.StatusEnum;
import com.wt.bl.enums.ValuableEnum;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * springmvc enum 的转换
 * Converter是一种类型转换另一种，可以用在很多层中
 * Formatter是String转换另一种，适用于web层，springmvc程序中推荐使用
 *
 * @author WangTao
 *         Created at 18/9/3 下午1:57.
 */
public class StatusEnumFormatter implements Formatter<StatusEnum> {

    @Override
    public StatusEnum parse(String text, Locale locale) throws ParseException {
        return ValuableEnum.getEnum(StatusEnum.class, Integer.parseInt(text));
    }

    @Override
    public String print(StatusEnum object, Locale locale) {
        return object.getValue().toString();
    }

}
