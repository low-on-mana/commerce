package com.uniblox.commerce.service;

import com.uniblox.commerce.model.Cart;
import com.uniblox.commerce.model.LineItem;
import com.uniblox.commerce.model.Order;
import com.uniblox.commerce.model.Product;
import com.uniblox.commerce.repository.OrderRepository;
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

    public Order checkOut() {
        Order order = fromCart(cart);
        orderRepository.save(order);
        return order;
    }

    public void addToCart(Long productId, Integer quantity) {
        cart.addToCart(productId, quantity);
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
