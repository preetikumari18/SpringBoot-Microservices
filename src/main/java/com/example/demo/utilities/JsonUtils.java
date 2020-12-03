package com.example.demo.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static String convertToJson(Object objectToConvert) {
        if (objectToConvert != null) {
            try {
                return objectMapper.writeValueAsString(objectToConvert);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting " + objectToConvert.getClass().getSimpleName() + " to JSON", e);
            }
        }
        return "{}";
    }
}
