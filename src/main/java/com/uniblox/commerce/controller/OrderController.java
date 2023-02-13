package com.uniblox.commerce.controller;

import com.uniblox.commerce.contracts.AddToCartRequest;
import com.uniblox.commerce.model.Order;
import com.uniblox.commerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/cart/add")
    public void addToCart(@RequestBody @Valid AddToCartRequest addToCartRequest) {
        orderService.addToCart(addToCartRequest.getProductId(), addToCartRequest.getQuantity());
    }

    @PostMapping("/checkout")
    public Order checkout() {
        return orderService.checkOut();
    }
}
