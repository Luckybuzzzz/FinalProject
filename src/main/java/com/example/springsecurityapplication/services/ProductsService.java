package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Product;

import java.util.List;

public interface ProductsService {
    List<Product> searchProducts (String title, Float ot, Float Do, Integer category, String priceOrder);
}
