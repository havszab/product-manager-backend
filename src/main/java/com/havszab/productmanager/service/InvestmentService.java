package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Investment;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.InvestmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InvestmentService {

    @Autowired
    private InvestmentRepo investmentRepo;

    public void save(Investment investment) {
        investmentRepo.save(investment);
    }

    public Investment get(Long id) {
        return investmentRepo.getOne(id);
    }

    public void set(Map data) {
        Long id = (long) (int) data.get("id");
        String title = (String) data.get("title");
        Long value = (long) (int) data.get("value");
        String description = (String) data.get("description");

        Investment investment = get(id);

        investment.setTitle(title);
        investment.setValue(value);
        investment.setDescription(description);

        save(investment);
    }

    public List<Investment> getAll(User user) {
        return investmentRepo.getAllByOwnerOrderByIdDesc(user);
    }
}
