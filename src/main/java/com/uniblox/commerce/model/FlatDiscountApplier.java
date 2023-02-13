package com.uniblox.commerce.model;

public class FlatDiscountApplier extends DiscountApplier {

    private final Discount discount;

    public FlatDiscountApplier(Discount discount) {
        super(discount);
        this.discount = discount;
    }

    @Override
    public void apply(Order order) {
        super.apply(order);
        order.provideDiscount(discount.getDiscountValue());
    }
}
