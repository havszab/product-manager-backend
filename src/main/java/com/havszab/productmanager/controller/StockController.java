package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.StockRepo;
import com.havszab.productmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StockController {

    @Autowired
    StockRepo stockRepo;

    @Autowired
    UserRepo userRepo;

    @CrossOrigin
    @PostMapping("/get-stock")
    public Map getStock(@RequestBody Map userData) {
        Map result = new HashMap();
        try {
            User user = userRepo.findByEmail((String) userData.get("email"));
            result.put("stock", stockRepo.findStockByOwner(user));
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }
}
