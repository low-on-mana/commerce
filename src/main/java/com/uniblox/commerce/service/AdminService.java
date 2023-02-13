package com.uniblox.commerce.service;

import com.uniblox.commerce.contracts.AdminSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final OrderService orderService;

    private final DiscountService discountService;

    public AdminSummary createSummary() {
        return AdminSummary.builder()
                .totalPurchaseAmount(orderService.totalPurchaseAmount())
                .totalDiscountAmount(orderService.totalDiscountAmount())
                .itemsPurchased(orderService.listOfItemsPurchased())
                .discountCodes(discountService.listOfDiscountCodes())
                .build();
    }
}
