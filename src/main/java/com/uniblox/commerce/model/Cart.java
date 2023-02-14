package com.uniblox.commerce.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Cart {
    private final Map<Long, Integer> items = new HashMap<>();

    public void addToCart(Long productId, Integer quantity) {
        items.put(productId, items.getOrDefault(productId, 0) + quantity);
    }

    public List<LineItem> getItems() {
        return items.entrySet().stream().map((Map.Entry<Long, Integer> entry) -> {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            return new LineItem(productId, quantity);
        }).collect(Collectors.toList());
    }

    public void clear() {
        items.clear();
    }
}
