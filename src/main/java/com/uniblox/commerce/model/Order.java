package com.uniblox.commerce.model;


import com.uniblox.commerce.converter.LineItemConverter;
import com.uniblox.commerce.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private Double amount;

    @Column
    @Builder.Default
    private Double discountAmount = 0.0;

    /**
     *  List of items purchased for this order, Ideally we should store this in another table to simplify querying.
     */
    @Column
    @Convert(converter = LineItemConverter.class)
    private List<LineItem> items;

    /**
     *  List of discounts applied for this order, Ideally we should store this in another table to simplify querying.
     */
    @Column
    @Convert(converter = StringListConverter.class)
    @Builder.Default
    private List<String> discountsApplied = new ArrayList<>();

    public void addDiscountApplied(String discountCode) {
        discountsApplied.add(discountCode);
    }

    public void provideDiscount(Double discountAmount) {
        if(this.amount >= discountAmount) {
            this.discountAmount = discountAmount;
            this.amount = this.amount - this.discountAmount;
        }
    }
}