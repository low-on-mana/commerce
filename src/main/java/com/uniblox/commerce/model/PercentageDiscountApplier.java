package com.uniblox.commerce.model;

public class PercentageDiscountApplier extends DiscountApplier {

    private final Discount discount;

    public PercentageDiscountApplier(Discount discount) {
        super(discount);
        this.discount = discount;
    }

    @Override
    public void apply(Order order) {
        super.apply(order);
        order.setAmount(order.getAmount() - (order.getAmount() * discount.getDiscountValue()) / 100);
    }
}
