package com.uniblox.commerce.model;


import com.uniblox.commerce.converter.LineItemConverter;
import jakarta.persistence.*;
import lombok.*;

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
    @Convert(converter = LineItemConverter.class)
    private List<LineItem> items;
}
