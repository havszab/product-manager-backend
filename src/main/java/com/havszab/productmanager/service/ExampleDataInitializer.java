package com.havszab.productmanager.service;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.model.enums.CostType;
import com.havszab.productmanager.repositories.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ExampleDataInitializer {

    public ExampleDataInitializer(ProductRepo productRepo,
                                  ProductCategoryRepo productCategoryRepo,
                                  UnitCategoryRepo unitCategoryRepo,
                                  UserRepo userRepo,
                                  AcquisitionRepo acquisitionRepo,
                                  StockRepo stockRepo,
                                  SalesRepo salesRepo,
                                  SoldProductRepo soldProductRepo,
                                  CostRepo costRepo,
                                  EmployeeRepo employeeRepo) {
        User user = new User("havszab@gmail.com", "admin");
        ProductCategory apple = new ProductCategory("apple");
        UnitCategory chest = new UnitCategory("chest");
        Product exampleProduct = new Product(apple, chest, (long) 30, (long) 30000);

        productCategoryRepo.save(apple);
        unitCategoryRepo.save(chest);
        productRepo.save(exampleProduct);

        userRepo.save(user);
        Acquisition exampleAcq = new Acquisition(user);

        Product secProd = new Product(apple, chest, (long) 40, (long) 50000);
        productRepo.save(secProd);
        HashSet<Product> products = new HashSet<>();
        products.add(exampleProduct);
        products.add(secProd);
        exampleAcq.setProducts(products);

        acquisitionRepo.save(exampleAcq);

        Stock stock = new Stock(user);
        Sales sales = new Sales(user);
        sales.setProducts(new HashSet<>());
        stockRepo.save(stock);
        salesRepo.save(sales);
        user.setStock(stock);
        user.setSales(sales);
        userRepo.save(user);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(new ProductCategory("salad"));
        productCategories.add(new ProductCategory("potato"));
        productCategories.add(new ProductCategory("tomato"));
        productCategories.add(new ProductCategory("cucumber"));
        productCategories.add(new ProductCategory("strawberry"));
        productCategories.add(new ProductCategory("cherry"));
        productCategories.add(new ProductCategory("sour cherry"));
        productCategories.add(new ProductCategory("garlic"));
        productCategories.add(new ProductCategory("banana"));
        productCategories.add(new ProductCategory("lettuce"));
        productCategories.add(new ProductCategory("cabbage"));
        productCategories.add(new ProductCategory("leek"));
        productCategories.add(new ProductCategory("mushroom"));
        productCategories.add(new ProductCategory("plum"));
        productCategories.add(new ProductCategory("pear"));
        productCategories.add(new ProductCategory("grape"));
        productCategories.add(new ProductCategory("peach"));
        productCategories.add(new ProductCategory("apricot"));
        productCategories.add(new ProductCategory("cauliflower"));
        productCategories.add(new ProductCategory("kohlrabi"));
        productCategories.add(new ProductCategory("eggplant"));
        productCategories.add(new ProductCategory("turnip"));
        productCategories.add(new ProductCategory("carrot"));


        for (ProductCategory cat : productCategories) {
            productCategoryRepo.save(cat);
        }

        Set<SoldProduct> soldProducts = sales.getProducts();
        Random rand = new Random();
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 12; j++) {
                for (int k = 0; k < 3; k++) {
                    Date sellingDate = new GregorianCalendar(2017 + k, j, i).getTime();

                    Long value = (long) rand.nextInt(30000) + 15000;
                    Long income = (long) rand.nextInt(60000) + 20000;

                    Long quantity = (long) rand.nextInt(50) + 30;
                    Long profit = income - value;

                    SoldProduct soldProduct = new SoldProduct(productCategories.get(rand.nextInt(productCategories.size())), chest, quantity, value, "", sellingDate, income, profit);
                    soldProducts.add(soldProduct);
                    soldProductRepo.save(soldProduct);

                }
            }
        }
        sales.setProducts(soldProducts);
        salesRepo.save(sales);

        costRepo.save(new Cost("Season trade ticket", CostType.ANNUAL, 450000, user));
        costRepo.save(new Cost("Insurance [Truck]", CostType.ANNUAL, 160000, user));
        costRepo.save(new Cost("Insurance [Car]", CostType.ANNUAL, 50000, user));
        costRepo.save(new Cost("Seasonal service [Truck]", CostType.ANNUAL, 100000, user));

        costRepo.save(new Cost("Car leasing cost", CostType.MONTHLY, 120000, user));
        costRepo.save(new Cost("Accountant", CostType.MONTHLY, 50000, user));

        costRepo.save(new Cost("Cafeteria", CostType.WEEKLY, 50000, user));
        costRepo.save(new Cost("Market entry ticket", CostType.WEEKLY, 10000, user));

        costRepo.save(new Cost("Car acquisition", CostType.OTHER, 2450000, user));
        costRepo.save(new Cost("Car administration", CostType.OTHER, 75000, user));
        costRepo.save(new Cost("Notebook acquisition", CostType.OTHER, 450000, user));
        costRepo.save(new Cost("Forklift acquisition", CostType.OTHER, 1250000, user));

        Employee employee = new Employee(
                "Havalda",
                "Szabolcs",
                "havszab@gmail.com",
                550000,
                "+36302112452",
                "TRADER",
                user
        );

        employeeRepo.save(employee);

        costRepo.save(new Cost(employee.getFirstName() + " " + employee.getLastName() + "'s salary", CostType.MONTHLY, employee.getSalary(), user));
    }
}
