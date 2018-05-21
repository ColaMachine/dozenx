package com.dozenx.web.core.spring.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:05 2018/2/27
 * @Modified By:
 */
public class Timestamp2long extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(date.getTime() );
    }
}
