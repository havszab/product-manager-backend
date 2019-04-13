package com.havszab.productmanager.service;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.repositories.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ExampleDataInitializer {

    public ExampleDataInitializer(ProductRepo productRepo,
                                  ProductCategoryRepo productCategoryRepo,
                                  UnitCategoryRepo unitCategoryRepo,
                                  UserRepo userRepo,
                                  AcquisitionRepo acquisitionRepo,
                                  StockRepo stockRepo) {
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

        User user = userRepo.findByEmail("havszab@gmail.com");
        Stock stock = new Stock(user);
        stockRepo.save(stock);
        user.setStock(stock);
        userRepo.save(user);

    }
}
