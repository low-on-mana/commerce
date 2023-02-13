package com.uniblox.commerce.contracts;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class AddToCartRequest implements Serializable {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}
