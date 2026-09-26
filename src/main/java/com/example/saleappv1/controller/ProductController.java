package com.example.saleappv1.controller;

import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // Danh sách sản phẩm + lọc/tìm kiếm
    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double fromPrice,
            @RequestParam(required = false) Double toPrice,
            Model model) {

        List<Product> result = productService.filterProducts(categoryId, keyword, fromPrice, toPrice);

        model.addAttribute("products", result);
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("fromPrice", fromPrice);
        model.addAttribute("toPrice", toPrice);

        return "products";
    }

    // Chi tiết 1 sản phẩm
    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }
}