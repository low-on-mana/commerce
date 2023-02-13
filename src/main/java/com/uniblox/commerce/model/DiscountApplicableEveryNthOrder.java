package com.uniblox.commerce.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscountApplicableEveryNthOrder implements IDiscountApplicability {

    private final Discount discount;

    @Override
    public boolean isApplicable(CustomerOrderProfile customerOrderProfile) {
        return customerOrderProfile.getNumOrders() == discount.getNumOrdersForDiscountApplicableEveryNthOrder();
    }
}
