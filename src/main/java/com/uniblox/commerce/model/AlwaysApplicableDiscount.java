package com.uniblox.commerce.model;

public class AlwaysApplicableDiscount implements IDiscountApplicability {
    @Override
    public boolean isApplicable(CustomerOrderProfile customerOrderProfile) {
        return true;
    }
}
