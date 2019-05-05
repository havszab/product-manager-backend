package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Action;
import com.havszab.productmanager.model.Cost;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.model.enums.ActionColor;
import com.havszab.productmanager.repositories.ActionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ActionService {

    @Autowired
    ActionRepo actionRepo;

    void saveAcquisitionFinishAction(int length, User user) {
        actionRepo.save(new Action(length + " products delivered to stock!", new Date(), user, ActionColor.BLUE));
    }

    void saveCostPayedAction(User user, Cost cost) {
        actionRepo.save(new Action(
                cost.getName() + "  payed. Amount: " + cost.getCost() + " HUF",
                new Date(),
                user,
                ActionColor.RED));
    }

    public List<Action> getTop10 (User user) {
        return actionRepo.getTop10ByOwnerOrderByDateDesc(user);
    }

    public List<Action> getTop5 (User user) {
        return actionRepo.getTop5ByOwnerOrderByDateDesc(user);
    }
}
