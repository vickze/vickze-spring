package io.vickze.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author vick.zeng
 * date-time: 2018/11/6 14:07
 **/
public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
    public static final ZonedDateTimeDeserializer INSTANCE = new ZonedDateTimeDeserializer();

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return ZonedDateTime.parse(p.getValueAsString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
