package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.Stock;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StockService {

    @Autowired
    StockRepo stockRepo;

    @Autowired
    ProductService productService;

    public Stock getStock (User user) {
        return stockRepo.findStockByOwner(user);
    }

    public void addProductsToStock (Stock stock, Set<Product> products) {
        Set<Product> currentStockProducts = stock.getProducts();
        productService.setProductsStatusToIN_STOCK(products);
        currentStockProducts.addAll(products);
        stock.setProducts(currentStockProducts);
        stockRepo.save(stock);
    }
}
