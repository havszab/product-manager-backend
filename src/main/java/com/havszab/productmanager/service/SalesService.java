package com.havszab.productmanager.service;

import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.SalesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@Component
public class SalesService {

    @Autowired
    private SalesRepo salesRepo;

    public Map getIncomeByYear(int year, User user) {
        return salesRepo.getIncomeByYear(
                new GregorianCalendar(year, 0, 1).getTime(),
                new GregorianCalendar(year, 11,31).getTime(),
                user.getId());
    }

    public Map getSoldProductsCount (User user) { return salesRepo.getSoldProductsCount(user); }

    public List<Map> getIncomesOfYears (User user) {
        return salesRepo.getIncomesOfYears(user);
    }

    public List<Map> getIProfitsOfYears (User user) {
        return salesRepo.getProfitsOfYears(user);
    }

}
