package com.uniblox.commerce.model;

public class DiscountApplierFactory {
    public static IDiscountApplier create(Discount discount) {
        switch (discount.getType()) {
            case FLAT -> {
                return new FlatDiscountApplier(discount);
            }
            case PERCENTAGE -> {
                return new PercentageDiscountApplier(discount);
            }
        }
        return new FlatDiscountApplier(discount);
    }
}
