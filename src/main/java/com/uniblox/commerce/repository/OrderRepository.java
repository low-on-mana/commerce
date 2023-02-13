package com.uniblox.commerce.repository;

import com.uniblox.commerce.model.LineItem;
import com.uniblox.commerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT sum(amount) from Order")
    Double totalPurchaseAmount();

    @Query("SELECT sum(discountAmount) from Order")
    Double totalDiscountAmount();

    @Query("SELECT items from Order")
    List<LineItem> findItems();
}
