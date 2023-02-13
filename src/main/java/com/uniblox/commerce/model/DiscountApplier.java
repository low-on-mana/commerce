package com.uniblox.commerce.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DiscountApplier {
    private final Discount discount;

    /**
     * Applies discount to an order by setting discountAmount and discountsApplied in an order.
     */
    public void apply(Order order) {
        order.addDiscountApplied(discount.getCode());
    }
}

