package com.uniblox.commerce.service;

import com.uniblox.commerce.exceptions.InvalidDiscountException;
import com.uniblox.commerce.model.*;
import com.uniblox.commerce.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public void applyDiscount(String discountCode, Order order, CustomerOrderProfile customerOrderProfile) {
        Discount discount = discountRepository.findByCode(discountCode).orElseThrow(InvalidDiscountException::new);

        IDiscountApplicability discountApplicability = DiscountApplicabilityFactory.create(discount);

        // We try to figure out whether discount should actually be applied here, it might be an always applicable discount
        // or a discount which is applied only on every nth order of a user
        if(discountApplicability.isApplicable(customerOrderProfile)) {

            IDiscountApplier discountApplier = DiscountApplierFactory.create(discount);

            // There might be multiple ways to do actual computation of discount ( flat, percentage ) which we do here.
            discountApplier.apply(order);
        }
    }
}
