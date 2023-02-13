package com.uniblox.commerce.service;

import com.uniblox.commerce.exceptions.InvalidDiscountException;
import com.uniblox.commerce.model.Discount;
import com.uniblox.commerce.model.Order;
import com.uniblox.commerce.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public void applyDiscount(String discountCode, Order order) {
        Discount discount = discountRepository.findByDiscountCode(discountCode).orElseThrow(InvalidDiscountException::new);
        switch (discount.getDiscountType()) {
            case FLAT -> order.setAmount(Math.max(order.getAmount() - discount.getDiscountValue(), 0));
            case PERCENTAGE ->
                    order.setAmount(order.getAmount() - (order.getAmount() * discount.getDiscountValue()) / 100);
        }
    }
}
