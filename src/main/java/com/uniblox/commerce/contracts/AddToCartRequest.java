package com.uniblox.commerce.contracts;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest implements Serializable {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}
