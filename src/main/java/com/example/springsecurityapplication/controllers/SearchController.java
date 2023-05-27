package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.services.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class SearchController {

    private final ProductSearchService productSearchService;

    public SearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @PostMapping("/product/search")
    public String productSearch(@RequestParam("search") String search,
                                @RequestParam("ot") String ot,
                                @RequestParam("do") String Do,
                                @RequestParam(value = "price", required = false, defaultValue = "") String price,
                                @RequestParam(value = "contract", required = false, defaultValue = "") String contract,
                                Model model){

        Integer category = null;
        if (!contract.isEmpty()) {
            switch (contract) {
                case "kkm":
                    category = 1;
                    break;
                case "printers":
                    category = 2;
                    break;
                case "scanners":
                    category = 3;
                    break;
            }
        }

        Float otFloat = ot.isEmpty() ? null : Float.parseFloat(ot);
        Float doFloat = Do.isEmpty() ? null : Float.parseFloat(Do);

        List<Product> searchProducts = productSearchService.searchProducts(search.toLowerCase(), otFloat, doFloat, category, price);
        model.addAttribute("search_product", searchProducts);

        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        return "/product/product";
    }
}
