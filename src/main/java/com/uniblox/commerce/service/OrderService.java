package com.uniblox.commerce.service;

import com.uniblox.commerce.contracts.AddToCartRequest;
import com.uniblox.commerce.contracts.CheckoutRequest;
import com.uniblox.commerce.model.*;
import com.uniblox.commerce.repository.OrderRepository;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    /**
     *  Using single cart for all users. Ideally we will have a session based cart ( redis/db ) to work for multiple
     *  users across multiple devices.
     */
    private final Cart cart;

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final DiscountService discountService;

    private final Validator validator;

    public Order checkOut(CheckoutRequest checkoutRequest) {
        Order order = fromCart(cart);
        if(validator.validate(checkoutRequest).isEmpty()) {
            discountService.applyDiscount(checkoutRequest.getDiscountCode(), order);
        }
        orderRepository.save(order);
        return order;
    }

    public void addToCart(AddToCartRequest addToCartRequest) {
        cart.addToCart(addToCartRequest.getProductId(), addToCartRequest.getQuantity());
    }

    public void clearCart() {
        cart.clear();
    }

    private Order fromCart(Cart cart) {
        double total = 0.0;
        for (LineItem lintItem : cart.getItems()) {
            Product product = productService.getProduct(lintItem.getProductId());
            total = total + product.getPrice() * lintItem.getQuantity();
        }

        return Order.builder()
                .amount(total)
                .items(cart.getItems())
                .build();
    }

}
