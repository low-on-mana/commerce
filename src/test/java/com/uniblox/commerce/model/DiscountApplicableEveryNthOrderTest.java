package com.uniblox.commerce.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiscountApplicableEveryNthOrderTest {

    @Test
    @DisplayName("isApplicable returns false when its first order of customer")
    void isApplicable() {
        CustomerOrderProfile customerOrderProfile = new CustomerOrderProfile(0);
        Discount discount = new Discount(3L, "FLAT_10_OFF_EVERY_3RD_ORDER", Discount.DiscountType.FLAT,
                Discount.DiscountApplicability.EVERY_NTH_ORDER, 10.0, Map.of("EVERY_NTH_ORDER", 3));

        DiscountApplicableEveryNthOrder discountApplicablity = new DiscountApplicableEveryNthOrder(discount);

        assertFalse(discountApplicablity.isApplicable(customerOrderProfile));
    }

    @Test
    @DisplayName("isApplicable returns true when its nth order of customer")
    void isApplicable2() {
        CustomerOrderProfile customerOrderProfile = new CustomerOrderProfile(2);
        Discount discount = new Discount(3L, "FLAT_10_OFF_EVERY_3RD_ORDER", Discount.DiscountType.FLAT,
                Discount.DiscountApplicability.EVERY_NTH_ORDER, 10.0, Map.of("EVERY_NTH_ORDER", 3));

        DiscountApplicableEveryNthOrder discountApplicablity = new DiscountApplicableEveryNthOrder(discount);

        assertTrue(discountApplicablity.isApplicable(customerOrderProfile));
    }
}