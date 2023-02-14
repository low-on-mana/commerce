package com.uniblox.commerce.service;

import com.uniblox.commerce.contracts.AddToCartRequest;
import com.uniblox.commerce.contracts.CheckoutRequest;
import com.uniblox.commerce.model.*;
import com.uniblox.commerce.repository.OrderRepository;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        // We create order object from cart
        Order order = fromCart(cart);

        // We create user profile which might be useful for figuring out which discount to give to user
        CustomerOrderProfile customerOrderProfile = createProfile();

        // If user doesn't provide a discount code, we chose one for him automatically in the system
        if(!checkoutRequest.isDiscountCodePresent()) {
            List<Discount> applicableDiscounts = discountService.findApplicableDiscounts(customerOrderProfile);
            if(!applicableDiscounts.isEmpty()) {
                checkoutRequest.setDiscountCode(applicableDiscounts.get(0).getCode());
            }
        }

        // We apply the discount if there is any discount code provided by user or automatically selected by system
        if(checkoutRequest.isDiscountCodePresent()) {
            discountService.applyDiscount(checkoutRequest.getDiscountCode(), order, customerOrderProfile);
        }

        orderRepository.save(order);
        cart.clear();
        return order;
    }

    public void addToCart(AddToCartRequest addToCartRequest) {
        cart.addToCart(addToCartRequest.getProductId(), addToCartRequest.getQuantity());
    }

    public Double totalPurchaseAmount() {
        return orderRepository.totalPurchaseAmount();
    }

    public Double totalDiscountAmount() {
        return orderRepository.totalDiscountAmount();
    }

    public List<LineItem> listOfItemsPurchased() {
        return orderRepository.findItems();
    }

    public void clearCart() {
        cart.clear();
    }

    private Order fromCart(Cart cart) {
        double total = 0.0;
        List<LineItem> orderLineItems = new ArrayList<>();
        for (LineItem lineItem : cart.getItems()) {
            Product product = productService.getProduct(lineItem.getProductId());
            total = total + product.getPrice() * lineItem.getQuantity();
            orderLineItems.add(lineItem);
        }

        return Order.builder()
                .amount(total)
                .items(orderLineItems)
                .build();
    }

    private CustomerOrderProfile createProfile() {
        return new CustomerOrderProfile(orderRepository.count());
    }

}
