package com.uniblox.commerce.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        try {
            return objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, new TypeReference<List<String>>() { });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
