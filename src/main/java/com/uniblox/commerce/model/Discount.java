package com.uniblox.commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    public enum DiscountType { FLAT, PERCENTAGE }

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String discountCode;

    @Column
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column
    private Double discountValue;
}