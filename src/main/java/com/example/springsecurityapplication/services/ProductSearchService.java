package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.repositories.ProductsSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService implements ProductsSearchRepository {
    @Autowired
    private final ProductRepository productRepository;

    public ProductSearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> searchProducts(String title, Float ot, Float Do, Integer category, String priceOrder) {
        if (ot != null && Do != null) {
            if (category != null) {
                if (priceOrder.equals("sorted_by_ascending_price")) {
                    return productRepository.findByTitleAndCategoryOrderByPriceAsc(title, ot, Do, category);
                } else if (priceOrder.equals("sorted_by_descending_price")) {
                    return productRepository.findByTitleAndCategoryOrderByPriceDesc(title, ot, Do, category);
                } else {
                    return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategoryId(title, ot, Do, category);
                }
            } else {
                if (priceOrder.equals("sorted_by_ascending_price")) {
                    return productRepository.findByTitleOrderByPriceAsc(title, ot, Do);
                } else if (priceOrder.equals("sorted_by_descending_price")) {
                    return productRepository.findByTitleOrderByPriceDesc(title, ot, Do);
                } else {
                    return productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(title, ot, Do);
                }
            }
        } else if (category != null) {
            if (priceOrder.equals("sorted_by_ascending_price")) {
                return productRepository.findByTitleAndCategoryOrderByPriceAsc(title, category);
            } else if (priceOrder.equals("sorted_by_descending_price")) {
                return productRepository.findByTitleAndCategoryOrderByPriceDesc(title, category);
            } else {
                return productRepository.findByTitleAndCategory(title, category);
            }
        } else {
            if (priceOrder.equals("sorted_by_ascending_price")) {
                return productRepository.findByTitleOrderByPriceAsc(title);
            } else if (priceOrder.equals("sorted_by_descending_price")) {
                return productRepository.findByTitleOrderByPriceDesc(title);
            } else {
                return productRepository.findByTitleContainingIgnoreCase(title);
            }
        }
    }
}
