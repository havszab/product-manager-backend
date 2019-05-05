package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Cost;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.model.enums.CostType;
import com.havszab.productmanager.repositories.CostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@Component
public class CostService {

    @Autowired
    private CostRepo costRepo;

    @Autowired
    private CostPaymentService costPaymentService;

    @Autowired
    private ActionService actionService;

    public List<Cost> getAll(User user) {
        return costRepo.findAllByOwnerOrderByIdDesc(user);
    }

    public Cost get(Long id) {
        return costRepo.getOne(id);
    }

    public void save(Cost cost){
        costRepo.save(cost);
    }

    public void set(Map data) {
        Long id = (long) (int) data.get("id");
        String title = (String) data.get("name");
        CostType type = (CostType) data.get("type");
        Double cost = Double.parseDouble((String) data.get("cost"));
        String dateInLong = (String) data.get("date");
        Date date = new Date(Long.parseLong(dateInLong));

        Cost costToSet = costRepo.getOne(id);
        costToSet.setName(title);
        costToSet.setType(type);
        costToSet.setCost(cost);
        costToSet.setPayedLastDate(date);
        costRepo.save(costToSet);
    }


    public void pay (User user, Cost cost) {
        cost.setPayedLastDate(new Date());
        save(cost);
        actionService.saveCostPayedAction(user, cost);
        costPaymentService.savePayment(user, cost);
    }

    public List<Map> getCostsByYear(int year, User user) {
        return costRepo.getCostsNamesAndAmountsPaidInSelectedYear(
                new GregorianCalendar(year, 0,1).getTime(),
                new GregorianCalendar(year, 11, 31).getTime(),
                user);
    }
    public List<Object> getTop5Annual(User user) {
        return costRepo.getTop5AnnualCostByOwner(user.getId());
    }
    public List<Object> getTop5Monthly(User user) {
        return costRepo.getTop5MonthlyCostByOwner(user.getId());
    }
    public List<Object> getTop5Weekly(User user) {
        return costRepo.getTop5WeeklyCostByOwner(user.getId());
    }
    public List<Object> getTop5Other(User user) {
        return costRepo.getTop5OtherCostByOwner(user.getId());
    }

    public List<Object> getSumsByTypes(User user) {
        return costRepo.getCostSumsByTypes(user.getId());
    }

}
