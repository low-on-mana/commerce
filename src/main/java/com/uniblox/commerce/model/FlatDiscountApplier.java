package com.uniblox.commerce.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlatDiscountApplier implements IDiscountApplier {

    private final Discount discount;

    @Override
    public void apply(Order order) {
        order.setAmount(Math.max(order.getAmount() - discount.getDiscountValue(), 0));
    }
}
