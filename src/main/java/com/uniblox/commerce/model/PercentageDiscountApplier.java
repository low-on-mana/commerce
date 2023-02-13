package com.uniblox.commerce.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PercentageDiscountApplier implements IDiscountApplier {

    private final Discount discount;

    @Override
    public void apply(Order order) {
        order.setAmount(order.getAmount() - (order.getAmount() * discount.getDiscountValue()) / 100);
    }
}
