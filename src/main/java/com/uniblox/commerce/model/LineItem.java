package com.uniblox.commerce.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LineItem implements Serializable {

    private Long productId;

    private Integer quantity;
}
