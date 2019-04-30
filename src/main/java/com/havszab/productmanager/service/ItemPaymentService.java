package com.havszab.productmanager.service;

import com.havszab.productmanager.model.ItemPayment;
import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.ItemPaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class ItemPaymentService {

    @Autowired
    ItemPaymentRepo itemPaymentRepo;

    public void persistAcquiredItemsAsPayment(Set<Product> acquiredProducts, User user) {
        try {
            for (Product product : acquiredProducts) {
                itemPaymentRepo.save(new ItemPayment(product, new Date(), user));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
