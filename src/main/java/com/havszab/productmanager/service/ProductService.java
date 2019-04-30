package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void setProductsStatusToIN_STOCK (Set<Product> products) {

    }
}
