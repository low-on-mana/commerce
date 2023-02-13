package com.uniblox.commerce.service;

import com.uniblox.commerce.exceptions.InvalidDiscountException;
import com.uniblox.commerce.model.*;
import com.uniblox.commerce.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

            DiscountApplier discountApplier = DiscountApplierFactory.create(discount);

            // There might be multiple ways to do actual computation of discount ( flat, percentage ) which we do here.
            discountApplier.apply(order);
        }
    }

    /**
     * This method is to figure out if any preexisting discounts in system are applicable for a order before checkout phase.
     * For example, if there is an AlwaysApplicable discount and a DiscountApplicableEveryNthOrder in system,
     * it will present users with both the discounts if applicable and user can choose one out of it.
     */
    public List<Discount> findApplicableDiscounts(CustomerOrderProfile customerOrderProfile) {
        return discountRepository.findAll().stream().filter(discount -> {
            IDiscountApplicability discountApplicability = DiscountApplicabilityFactory.create(discount);
            return discountApplicability.isApplicable(customerOrderProfile);
        }).collect(Collectors.toList());
    }

    public List<String> listOfDiscountCodes() {
        return discountRepository.findCode();
    }
}
