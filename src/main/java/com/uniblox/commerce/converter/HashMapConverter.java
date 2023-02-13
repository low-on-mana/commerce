package com.uniblox.commerce.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@RequiredArgsConstructor
public class HashMapConverter implements AttributeConverter<HashMap<String, Object>, String> {
    private final ObjectMapper objectMapper;
    @Override
    public String convertToDatabaseColumn(HashMap<String, Object> stringObjectHashMap) {
        try {
            return objectMapper.writeValueAsString(stringObjectHashMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<String, Object> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, new TypeReference<HashMap<String, Object>>() { });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
