package com.havszab.productmanager.service;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.repositories.CostPaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CostPaymentService {

    @Autowired
    CostPaymentRepo costPaymentRepo;

    public void savePayment(User user, Cost cost) {
        costPaymentRepo.save(new CostPayment(cost, new Date(), user));
    }
}
