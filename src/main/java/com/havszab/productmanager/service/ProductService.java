package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.enums.Status;
import com.havszab.productmanager.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void setProductsStatusToIN_STOCK (Set<Product> products) {
        for (Product product : products) {
            product.setStatus(Status.IN_STOCK);
            productRepo.save(product);
        }
    }

    public void persistProduct(Product product) {
        productRepo.save(product);
    }

    public Product persistAndGetProduct(Product product) {
        productRepo.save(product);
        return product;
    }

    public Product getById(Long id) {
        return productRepo.getOne(id);
    }
}
