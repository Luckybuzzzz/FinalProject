package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Product;

import java.util.List;

public interface ProductsSearchRepository {
    List<Product> searchProducts (String title, Float ot, Float Do, Integer category, String priceOrder);
}
