package com.uniblox.commerce.service;

import com.uniblox.commerce.contracts.AddToCartRequest;
import com.uniblox.commerce.contracts.CheckoutRequest;
import com.uniblox.commerce.model.Cart;
import com.uniblox.commerce.model.Discount;
import com.uniblox.commerce.model.Order;
import com.uniblox.commerce.model.Product;
import com.uniblox.commerce.repository.DiscountRepository;
import com.uniblox.commerce.repository.OrderRepository;
import com.uniblox.commerce.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private Cart cart;

    @BeforeEach
    void setup() {
        productRepository.save(new Product(1L, "gloves", 20.0));
        productRepository.save(new Product(2L, "hat", 25.0));
        productRepository.save(new Product(3L, "cap", 30.0));

    }

    @AfterEach
    void clear() {
        orderService.clearCart();
        productRepository.deleteAll();
        discountRepository.deleteAll();
    }

    @Test
    @DisplayName("checkout correctly creates and save order")
    @Transactional
    void checkOut() {
        orderService.addToCart(new AddToCartRequest(1L, 1));
        orderService.addToCart(new AddToCartRequest(3L, 3));
        Order order = orderService.checkOut(new CheckoutRequest());

        assertEquals(110.0, order.getAmount());
        assertEquals(1, orderRepository.findAll().size());

        Order savedOrder =  orderRepository.findAll().stream().findFirst().get();
        assertEquals(110.0, savedOrder.getAmount());
        assertEquals(0.0, savedOrder.getDiscountAmount());
        assertEquals(2, savedOrder.getItems().size());
    }

    @Test
    @DisplayName("checkout correctly applies discount when provided in checkout request")
    @Transactional
    void checkout2() {
        discountRepository.save(new Discount(1L, "FLAT_50_OFF", Discount.DiscountType.FLAT,
                Discount.DiscountApplicability.ALWAYS, 50.0, null));


        orderService.addToCart(new AddToCartRequest(1L, 1));
        orderService.addToCart(new AddToCartRequest(3L, 3));
        Order order = orderService.checkOut(new CheckoutRequest("FLAT_50_OFF"));

        assertEquals(60.0, order.getAmount());
        assertEquals(1, orderRepository.findAll().size());

        Order savedOrder =  orderRepository.findAll().stream().findFirst().get();
        assertEquals(60.0, savedOrder.getAmount());
        assertEquals(2, savedOrder.getItems().size());
        assertEquals(50.0, savedOrder.getDiscountAmount());
        assertEquals(List.of("FLAT_50_OFF"), savedOrder.getDiscountsApplied());
    }

    @Test
    @DisplayName("checkout automatically picks and applies discount when not provided in checkout request")
    @Transactional
    void checkout3() {
        discountRepository.save(new Discount(1L, "FLAT_50_OFF", Discount.DiscountType.FLAT,
                Discount.DiscountApplicability.ALWAYS, 50.0, null));


        orderService.addToCart(new AddToCartRequest(1L, 1));
        orderService.addToCart(new AddToCartRequest(3L, 3));
        Order order = orderService.checkOut(new CheckoutRequest());

        assertEquals(60.0, order.getAmount());
        assertEquals(1, orderRepository.findAll().size());

        Order savedOrder =  orderRepository.findAll().stream().findFirst().get();
        assertEquals(60.0, savedOrder.getAmount());
        assertEquals(2, savedOrder.getItems().size());
        assertEquals(50.0, savedOrder.getDiscountAmount());
        assertEquals(List.of("FLAT_50_OFF"), savedOrder.getDiscountsApplied());
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