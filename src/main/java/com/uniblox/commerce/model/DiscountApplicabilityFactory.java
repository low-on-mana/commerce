package com.uniblox.commerce.model;

public class DiscountApplicabilityFactory {

    public static IDiscountApplicability create(Discount discount) {
        switch (discount.getApplicability()) {
            case ALWAYS -> {
                return new AlwaysApplicableDiscount();
            }
            case EVERY_NTH_ORDER -> {
                return new DiscountApplicableEveryNthOrder(discount);
            }
        }
        return new AlwaysApplicableDiscount();
    }
}
