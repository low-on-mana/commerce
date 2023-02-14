package com.uniblox.commerce.model;

import com.uniblox.commerce.converter.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    public enum DiscountType { FLAT, PERCENTAGE }

    public enum DiscountApplicability { ALWAYS, EVERY_NTH_ORDER };

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String code;

    @Column
    @Enumerated(EnumType.STRING)
    private DiscountType type = DiscountType.FLAT;

    @Column
    @Enumerated(EnumType.STRING)
    private DiscountApplicability applicability = DiscountApplicability.ALWAYS;

    @Column
    private Double discountValue;

    /**
     *  Since discounts can have variety of applicability conditions, we need a flexible column to store them ( like a
     *  metadata map ). Discount could be applicable on dateOfBirth, holidays, everyNthOrder, alwaysApplicable etc.
     *  TODO: To create a class on top of this map for simplicity
     */
    @Column
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> metadata = new HashMap<>();

    public void setDiscountApplicableEveryNthOrder(int numOrders) {
        this.applicability = DiscountApplicability.EVERY_NTH_ORDER;
        this.metadata.put(DiscountApplicability.EVERY_NTH_ORDER.toString(), numOrders);
    }

    public int getNumOrdersForDiscountApplicableEveryNthOrder() {
        return (int) this.metadata.getOrDefault(DiscountApplicability.EVERY_NTH_ORDER.toString(), -1);
    }
}