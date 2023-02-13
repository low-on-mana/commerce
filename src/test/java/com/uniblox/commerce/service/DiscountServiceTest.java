package com.uniblox.commerce.service;

import com.uniblox.commerce.exceptions.InvalidDiscountException;
import com.uniblox.commerce.model.CustomerOrderProfile;
import com.uniblox.commerce.model.Discount;
import com.uniblox.commerce.model.Order;
import com.uniblox.commerce.repository.DiscountRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountRepository discountRepository;

    @BeforeEach
    public void setUp() {
        discountRepository.save(new Discount(1L, "FLAT_50_OFF", Discount.DiscountType.FLAT,
                Discount.DiscountApplicability.ALWAYS, 50.0, null));
        discountRepository.save(new Discount(2L, "10_PERCENT_OFF", Discount.DiscountType.PERCENTAGE,
                Discount.DiscountApplicability.ALWAYS, 10.0, null));
        Discount discount = new Discount(3L, "FLAT_10_OFF_EVERY_3RD_ORDER", Discount.DiscountType.FLAT,
                null, 10.0, new HashMap<>());
        discount.setDiscountApplicableEveryNthOrder(3);
        discountRepository.save(discount);
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

        discountService.applyDiscount("FLAT_50_OFF", order, null);

        assertEquals(order.getAmount(), 50.0);
    }

    @Test
    @DisplayName("applyDiscount applies percentage discount correctly")
    public void applyDiscount2() {
        Order order = new Order();
        order.setAmount(100.0);

        discountService.applyDiscount("10_PERCENT_OFF", order, null);

        assertEquals(order.getAmount(), 90.0);
    }

    @Test
    @DisplayName("applyDiscount throws exception if discount code is not found")
    public void applyDiscount3() {
        Order order = new Order();
        order.setAmount(100.0);

        assertThrows(InvalidDiscountException.class, () -> discountService.applyDiscount("MY_OWN_DISCOUNT_CODE"
                ,order , null));
    }

    @Test
    @DisplayName("applyDiscount runs correctly for nth order discount")
    public void applyDiscount4() {
        Order order = new Order();
        order.setAmount(100.0);

        CustomerOrderProfile customerOrderProfile = new CustomerOrderProfile(3);


        discountService.applyDiscount("FLAT_10_OFF_EVERY_3RD_ORDER", order, customerOrderProfile);

        Assertions.assertEquals(90.0, order.getAmount());
    }

    @Test
    @DisplayName("applyDiscount is ignored silently when nth order condition doesnt matches")
    public void applyDiscount5() {
        Order order = new Order();
        order.setAmount(100.0);

        CustomerOrderProfile customerOrderProfile = new CustomerOrderProfile(10);

        discountService.applyDiscount("FLAT_10_OFF_EVERY_3RD_ORDER", order, customerOrderProfile);

        Assertions.assertEquals(100.0, order.getAmount());
    }

    @Test
    @DisplayName("findApplicableDiscounts correctly returns number of applicable discounts")
    void findApplicableDiscounts() {
        CustomerOrderProfile customerOrderProfile = new CustomerOrderProfile(3);
        List<Discount> applicableDiscounts = discountService.findApplicableDiscounts(customerOrderProfile);
        // 3 discounts are applicable which are created in beginning of test
        assertEquals(3, applicableDiscounts.size());

        CustomerOrderProfile customerOrderProfile2 = new CustomerOrderProfile(10);
        // since nth order discount is not applicable, only 2 discounts are returned
        List<Discount> applicableDiscounts2 = discountService.findApplicableDiscounts(customerOrderProfile2);
        assertEquals(2, applicableDiscounts2.size());
    }
}