package com.uniblox.commerce.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiscountApplicableEveryNthOrder implements IDiscountApplicability {

    private final Discount discount;

    @Override
    public boolean isApplicable(CustomerOrderProfile customerOrderProfile) {
        // This condition will never happen, its just a guard rail to prevent divide by 0 crash in case of inconsistent
        // data in system
        if(discount.getNumOrdersForDiscountApplicableEveryNthOrder() <= 0) {
            return false;
        }
        return customerOrderProfile.getNumOrders() % discount.getNumOrdersForDiscountApplicableEveryNthOrder() == 0;
    }
}
