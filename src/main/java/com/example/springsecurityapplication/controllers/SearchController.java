package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.services.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
@Controller
public class SearchController {

    private final ProductSearchService productSearchService;
    @Autowired
    private final CategoryRepository categoryRepository;

    public SearchController(ProductSearchService productSearchService, CategoryRepository categoryRepository) {
        this.productSearchService = productSearchService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/product/search")
    public String productSearch(@RequestParam("search") String search,
                                @RequestParam("ot") String ot,
                                @RequestParam("do") String Do,
                                @RequestParam(value = "price", required = false, defaultValue = "") String price,
                                @RequestParam(value = "contract", required = false, defaultValue = "") String contract,
                                Model model){

        Integer category = null;
        String categoryName = "";
        if (!contract.isEmpty()) {
            switch (contract) {
                case "kkm":
                    category = 1;
                    categoryName = categoryRepository.findNameById(category);
                    break;
                case "printers":
                    category = 2;
                    categoryName = categoryRepository.findNameById(category);
                    break;
                case "scanners":
                    category = 3;
                    categoryName = categoryRepository.findNameById(category);
                    break;
                case "scales":
                    category = 4;
                    categoryName = categoryRepository.findNameById(category);
                    break;
                case "detectors":
                    category = 5;
                    categoryName = categoryRepository.findNameById(category);
                    break;
            }
        }

        Float otFloat = ot.isEmpty() ? null : Float.parseFloat(ot);
        Float doFloat = Do.isEmpty() ? null : Float.parseFloat(Do);

        List<Product> searchProducts = productSearchService.searchProducts(search.toLowerCase(), otFloat, doFloat, category, price);
        model.addAttribute("search_product", searchProducts);

        List<String> activeFilters = new ArrayList<>();
        if (!search.isEmpty()) activeFilters.add("Поиск: " + search);
        if (!ot.isEmpty()) activeFilters.add("Цена от: " + ot);
        if (!Do.isEmpty()) activeFilters.add("Цена до: " + Do);
        if (price.equals("sorted_by_ascending_price")) activeFilters.add("Сортировка: По возрастанию цены");
        else if (price.equals("sorted_by_descending_price")) activeFilters.add("Сортировка: По убыванию цены");
        if (!contract.isEmpty()) activeFilters.add("Категория: " + categoryName);
        model.addAttribute("active_filters", activeFilters);

        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        return "/product/product";
    }
}
