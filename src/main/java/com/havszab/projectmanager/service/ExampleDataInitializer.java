package com.havszab.projectmanager.service;

import com.havszab.projectmanager.model.Product;
import com.havszab.projectmanager.model.ProductCategory;
import com.havszab.projectmanager.model.UnitCategory;
import com.havszab.projectmanager.model.User;
import com.havszab.projectmanager.repositories.ProductCategoryRepo;
import com.havszab.projectmanager.repositories.ProductRepo;
import com.havszab.projectmanager.repositories.UnitCategoryRepo;
import com.havszab.projectmanager.repositories.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class ExampleDataInitializer {

    public ExampleDataInitializer(ProductRepo productRepo,
                                  ProductCategoryRepo productCategoryRepo,
                                  UnitCategoryRepo unitCategoryRepo,
                                  UserRepo userRepo) {
        ProductCategory apple = new ProductCategory("apple");
        UnitCategory chest = new UnitCategory("chest");
        Product exampleProduct = new Product(apple, chest, (long) 30, (long) 30000);

        productCategoryRepo.save(apple);
        unitCategoryRepo.save(chest);
        productRepo.save(exampleProduct);

        userRepo.save(new User("havszab@gmail.com", "admin"));
//
    }
}
