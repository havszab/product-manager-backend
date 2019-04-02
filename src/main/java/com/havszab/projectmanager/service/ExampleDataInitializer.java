package com.havszab.projectmanager.service;

import com.havszab.projectmanager.model.*;
import com.havszab.projectmanager.repositories.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class ExampleDataInitializer {

    public ExampleDataInitializer(ProductRepo productRepo,
                                  ProductCategoryRepo productCategoryRepo,
                                  UnitCategoryRepo unitCategoryRepo,
                                  UserRepo userRepo,
                                  AcquisitionRepo acquisitionRepo) {
        ProductCategory apple = new ProductCategory("apple");
        UnitCategory chest = new UnitCategory("chest");
        Product exampleProduct = new Product(apple, chest, (long) 30, (long) 30000);

        productCategoryRepo.save(apple);
        unitCategoryRepo.save(chest);
        productRepo.save(exampleProduct);

        userRepo.save(new User("havszab@gmail.com", "admin"));
        Acquisition exampleAcq = new Acquisition(userRepo.findByEmail("havszab@gmail.com"));

        Product secProd = new Product(apple, chest, (long) 40, (long) 50000);
        productRepo.save(secProd);
        HashSet<Product> products = new HashSet<>();
        products.add(exampleProduct);
        products.add(secProd);
        exampleAcq.setProducts(products);

        acquisitionRepo.save(exampleAcq);

    }
}
