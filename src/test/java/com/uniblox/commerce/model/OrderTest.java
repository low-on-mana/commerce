package com.uniblox.commerce.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    @DisplayName("provideDiscount should set discounted amount for order")
    void provideDiscount() {
        Order order = new Order();
        order.setAmount(500.0);
        order.provideDiscount(250.0);
        assertEquals(250.0, order.getAmount());
        assertEquals(250.0, order.getDiscountAmount());
    }
    @Test
    @DisplayName("provideDiscount should not set discounted amount for order when amount is smaller than discount")
    void provideDiscount2() {
        Order order = new Order();
        order.setAmount(200.0);
        order.provideDiscount(250.0);
        assertEquals(200.0, order.getAmount());
        assertEquals(0.0, order.getDiscountAmount());
    }
}