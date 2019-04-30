package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Acquisition;
import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.Stock;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.AcquisitionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AcquisitionService {

    @Autowired
    AcquisitionRepo acquisitionRepo;

    @Autowired
    StockService stockService;

    @Autowired
    ActionService actionService;

    public void addProduct(Product product, User user) {
        Acquisition acquisition = acquisitionRepo.findAcquisitionByOwner(user);
        Set<Product> currentProducts = acquisition.getProducts();
        currentProducts.add(product);
        acquisitionRepo.save(acquisition);
    }

    public Acquisition get(User user) {
        return acquisitionRepo.findAcquisitionByOwner(user);
    }

    private void removeItems (Acquisition acquisition, Set<Product> products) {
        acquisition.getProducts().removeAll(products);
        acquisitionRepo.save(acquisition);
    }

    public void removeItems(User user, Set<Product> products) {
        removeItems(get(user), products);
    }

    public void moveAllItemsToStock(User user) {
        Acquisition acquisition = get(user);
        moveSelectedItemsToStock(user, acquisition.getProducts());
    }

    public void moveSelectedItemsToStock(User user, Set<Product> products) {
        Acquisition acquisition = get(user);
        Stock stock = stockService.getStock(user);
        stockService.addProductsToStock(stock, products);
        actionService.saveAcquisitionFinishAction(products.size(), user);
        removeItems(acquisition, products); //after that, products will be empty
    }

}
