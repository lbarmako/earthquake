package com.intfinit.earthquakes.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    protected OffsetDateTimeDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return OffsetDateTime.parse(jp.readValueAs(String.class));
    }
}