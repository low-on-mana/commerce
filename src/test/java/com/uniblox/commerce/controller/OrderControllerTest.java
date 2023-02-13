package com.uniblox.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniblox.commerce.contracts.AddToCartRequest;
import com.uniblox.commerce.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("addToCart api return 400 if either of field is null")
    void addToCart() throws Exception {
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setProductId(1L);
        addToCartRequest.setQuantity(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addToCartRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(orderService, times(0)).addToCart(any(), any());
    }

    @DisplayName("addToCart api return 200 when request is valid")
    @Test
    void addToCart2() throws Exception {
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setProductId(1L);
        addToCartRequest.setQuantity(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/orders/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addToCartRequest)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(orderService, times(1)).addToCart(1L, 1);
    }
}