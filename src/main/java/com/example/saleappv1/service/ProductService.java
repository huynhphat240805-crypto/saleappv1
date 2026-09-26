package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public ProductService() {
        loadProducts();
        loadCategories();
    }

    private void loadProducts() {
        try {
            JsonMapper mapper = JsonMapper.builder().build();
            InputStream is = new ClassPathResource("data/products.json").getInputStream();
            Product[] arr = mapper.readValue(is, Product[].class);
            products = new ArrayList<>(List.of(arr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCategories() {
        try {
            JsonMapper mapper = JsonMapper.builder().build();
            InputStream is = new ClassPathResource("data/categories.json").getInputStream();
            Category[] arr = mapper.readValue(is, Category[].class);
            categories = new ArrayList<>(List.of(arr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Category> getAllCategories() {
        return categories;
    }

    public List<Product> filterProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        List<Product> result = new ArrayList<>(products);

        if (categoryId != null) {
            result.removeIf(p -> p.getCategoryId() != categoryId);
        }

        if (keyword != null && !keyword.isBlank()) {
            String kw = keyword.toLowerCase();
            result.removeIf(p -> !p.getName().toLowerCase().contains(kw));
        }

        if (fromPrice != null) {
            result.removeIf(p -> p.getPrice() < fromPrice);
        }

        if (toPrice != null) {
            result.removeIf(p -> p.getPrice() > toPrice);
        }

        return result;
    }
}