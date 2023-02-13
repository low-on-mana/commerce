package com.uniblox.commerce.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniblox.commerce.model.LineItem;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LineItemConverter implements AttributeConverter<List<LineItem>, String> {
    private final ObjectMapper objectMapper;
    @Override
    public String convertToDatabaseColumn(List<LineItem> lineItem) {
        try {
            return objectMapper.writeValueAsString(lineItem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LineItem> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, new TypeReference<List<LineItem>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
