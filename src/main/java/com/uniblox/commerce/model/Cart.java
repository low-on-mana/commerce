package com.uniblox.commerce.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {
    private final List<LineItem> items = new ArrayList<>();

    public void addToCart(Long productId, Integer quantity) {
        items.add(new LineItem(productId, quantity));
    }

    public void clear() {
        items.clear();
    }
}
