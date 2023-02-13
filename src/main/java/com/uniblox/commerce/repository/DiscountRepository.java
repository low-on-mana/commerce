package com.uniblox.commerce.repository;

import com.uniblox.commerce.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCode(String code);

    @Query("SELECT code from Discount")
    List<String> findCode();
}
