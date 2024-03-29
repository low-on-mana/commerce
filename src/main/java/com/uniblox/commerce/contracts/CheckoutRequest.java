package com.uniblox.commerce.contracts;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest implements Serializable {

    private String discountCode;

    public boolean isDiscountCodePresent() {
        return discountCode != null && !discountCode.isEmpty();
    }

}
