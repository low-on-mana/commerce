package com.uniblox.commerce.service;

import com.uniblox.commerce.exceptions.InvalidDiscountException;
import com.uniblox.commerce.model.Discount;
import com.uniblox.commerce.model.Order;
import com.uniblox.commerce.repository.DiscountRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountRepository discountRepository;

    @BeforeEach
    public void setUp() {
        discountRepository.save(new Discount(1L, "FLAT_50_OFF", Discount.DiscountType.FLAT, 50.0));
        discountRepository.save(new Discount(1L, "10_PERCENT_OFF", Discount.DiscountType.PERCENTAGE, 10.0));
    }

    @AfterEach
    public void tearDown() {
        discountRepository.deleteAll();
    }

    @Test
    @DisplayName("applyDiscount applies flat discount correctly")
    public void applyDiscount() {
        Order order = new Order();
        order.setAmount(100.0);

        discountService.applyDiscount("FLAT_50_OFF", order);

        assertEquals(order.getAmount(), 50.0);
    }

    @Test
    @DisplayName("applyDiscount applies percentage discount correctly")
    public void applyDiscount2() {
        Order order = new Order();
        order.setAmount(100.0);

        discountService.applyDiscount("10_PERCENT_OFF", order);

        assertEquals(order.getAmount(), 90.0);
    }

    @Test
    @DisplayName("applyDiscount throws exception if discount code is not found")
    public void applyDiscount3() {
        Order order = new Order();
        order.setAmount(100.0);

        assertThrows(InvalidDiscountException.class, () -> discountService.applyDiscount("MY_OWN_DISCOUNT_CODE", order));
    }

}