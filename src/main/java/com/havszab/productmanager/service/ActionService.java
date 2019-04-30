package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Action;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.model.enums.ActionColor;
import com.havszab.productmanager.repositories.ActionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ActionService {

    @Autowired
    ActionRepo actionRepo;

    void saveAcquisitionFinishAction(int length, User user) {
        actionRepo.save(new Action(length + " products delivered to stock!", new Date(), user, ActionColor.BLUE));
    }
}
