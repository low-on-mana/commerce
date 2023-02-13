package com.uniblox.commerce.service;

import com.uniblox.commerce.contracts.AddToCartRequest;
import com.uniblox.commerce.model.Cart;
import com.uniblox.commerce.model.Order;
import com.uniblox.commerce.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Cart cart;

    @Test
    @DisplayName("checkout correctly creates and save order")
    void checkOut() {
        orderService.addToCart(new AddToCartRequest(1L, 1));
        orderService.addToCart(new AddToCartRequest(3L, 3));
        Order order = orderService.checkOut();

        assertEquals(110.0, order.getAmount());
        assertEquals(1, orderRepository.findAll().size());

        Order savedOrder =  orderRepository.findAll().stream().findFirst().get();
        assertEquals(110.0, savedOrder.getAmount());
        assertEquals(2, savedOrder.getItems().size());

        orderService.clearCart();
    }

    @Test
    @DisplayName("addToCart correctly adds item to the cart")
    void addToCart() {

        orderService.addToCart(new AddToCartRequest(1L, 1));
        orderService.addToCart(new AddToCartRequest(3L, 3));

        assertEquals(2, cart.getItems().size());
        assertEquals(1L, cart.getItems().get(0).getProductId());
        assertEquals(1, cart.getItems().get(0).getQuantity());
        assertEquals(3L, cart.getItems().get(1).getProductId());
        assertEquals(3, cart.getItems().get(1).getQuantity());

        orderService.clearCart();
    }

    @Test
    @DisplayName("clearCart removes all items from cart")
    void clearCart() {
        orderService.addToCart(new AddToCartRequest(1L, 1));
        orderService.addToCart(new AddToCartRequest(3L, 3));

        assertEquals(2, cart.getItems().size());

        orderService.clearCart();

        assertEquals(0, cart.getItems().size());
    }
}