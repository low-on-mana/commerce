package com.uniblox.commerce.controller;

import com.uniblox.commerce.model.Product;
import com.uniblox.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    private List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}