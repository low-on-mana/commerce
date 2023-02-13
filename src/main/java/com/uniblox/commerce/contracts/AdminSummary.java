package com.uniblox.commerce.contracts;

import com.uniblox.commerce.model.LineItem;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSummary implements Serializable {

    private Double totalPurchaseAmount;

    private Double totalDiscountAmount;

    private List<String> discountCodes;

    private List<LineItem> itemsPurchased;
}
