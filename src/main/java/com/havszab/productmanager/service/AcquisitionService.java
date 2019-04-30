package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Acquisition;
import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.AcquisitionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AcquisitionService {

    @Autowired
    AcquisitionRepo acquisitionRepo;

    public void addProduct(Product product, User user) {
        Acquisition acquisition = acquisitionRepo.findAcquisitionByOwner(user);
        Set<Product> currentProducts = acquisition.getProducts();
        currentProducts.add(product);
        acquisitionRepo.save(acquisition);
    }

    public Acquisition getAcquisition(User user) {
        return acquisitionRepo.findAcquisitionByOwner(user);
    }
}
