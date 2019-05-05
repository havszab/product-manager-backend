package com.havszab.productmanager.service;

import com.havszab.productmanager.model.ItemPayment;
import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.ItemPaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ItemPaymentService {

    @Autowired
    ItemPaymentRepo itemPaymentRepo;

    public void persistAcquiredItemsAsPayment(User user, Set<Product> acquiredProducts) {
        for (Product product : acquiredProducts) {
            itemPaymentRepo.save(new ItemPayment(product, new Date(), user));
        }
    }

    public Map getProductCostsOfSelectedYear(int year, User user) {
        return itemPaymentRepo.getProductCostsOfSelectedYear(new GregorianCalendar(year, 0,1).getTime(), new GregorianCalendar(year, 11,31).getTime(), user);
    }
}
