package com.wt.bl.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author WangTao
 *         Created at 18/9/3 下午7:08.
 */
public class ValuableEnumDeserializer<E extends Enum<E> & ValuableEnum> extends StdDeserializer<E> {

    private Class<E> clazz;

    public ValuableEnumDeserializer(Class<?> vc) {
        super(vc);
        clazz = (Class<E>) vc;
    }

    @Override
    public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Integer v = p.readValueAs(Integer.class);
        return ValuableEnum.getEnum(clazz, v);
    }
}
