package com.havszab.productmanager.service;

import com.havszab.productmanager.model.ProductCategory;
import com.havszab.productmanager.repositories.ProductCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepo productCategoryRepo;

    public ProductCategory getByName(String name) {
        return productCategoryRepo.findByProductName(name);
    }
}
