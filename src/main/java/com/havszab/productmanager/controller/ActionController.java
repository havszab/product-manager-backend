package com.havszab.productmanager.controller;

import com.havszab.productmanager.repositories.ActionRepo;
import com.havszab.productmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ActionController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ActionRepo actionRepo;

    @CrossOrigin
    @GetMapping("get-recent-actions")
    public Map getRecentActions(@RequestParam String email) {
        Map result = new HashMap();
        result.put("actions", actionRepo.getTop10ByOwnerOrderByDateDesc(userRepo.findByEmail(email)));
        return result;
    }

}
