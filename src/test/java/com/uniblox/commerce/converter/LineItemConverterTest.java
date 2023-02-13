package com.uniblox.commerce.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniblox.commerce.model.LineItem;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineItemConverterTest {

    private final LineItemConverter lineItemConverter = new LineItemConverter(new ObjectMapper());
    @Test
    @DisplayName("convertToDatabaseColumn converts to string correctly for multiple line items")
    void convertToDatabaseColumn() {
        List<LineItem> lineItems = List.of(new LineItem(1L, 1), new LineItem(2L, 1));
        String lineItemDbString = lineItemConverter.convertToDatabaseColumn(lineItems);
        Assertions.assertEquals("[{\"productId\":1,\"quantity\":1},{\"productId\":2,\"quantity\":1}]", lineItemDbString);
    }

    @Test
    @DisplayName("convertToEntityAttribute converts to line item list correctly from string")
    void convertToEntityAttribute() {
        String lineItemDbString = "[{\"productId\":1,\"quantity\":1},{\"productId\":2,\"quantity\":1}]";
        List<LineItem> lineItems = lineItemConverter.convertToEntityAttribute(lineItemDbString);
        assertEquals(lineItems.size(), 2);
        assertEquals(lineItems.get(0).getProductId(), 1L);
        assertEquals(lineItems.get(0).getQuantity(), 1);
        assertEquals(lineItems.get(1).getProductId(), 2L);
        assertEquals(lineItems.get(1).getQuantity(), 1);
    }
}