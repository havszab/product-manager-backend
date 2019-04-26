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
    @GetMapping("get-recent-10-actions")
    public Map getRecent10Actions(@RequestParam String email) {
        Map result = new HashMap();
        try {
            result.put("actions", actionRepo.getTop10ByOwnerOrderByDateDesc(userRepo.findByEmail(email)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    @CrossOrigin
    @GetMapping("get-recent-5-actions")
    public Map getRecent5Actions(@RequestParam String email) {
        Map result = new HashMap();
        try {
        result.put("actions", actionRepo.getTop5ByOwnerOrderByDateDesc(userRepo.findByEmail(email)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }


}
