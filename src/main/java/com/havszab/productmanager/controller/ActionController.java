package com.havszab.productmanager.controller;

import com.havszab.productmanager.service.ActionService;
import com.havszab.productmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ActionController {


    private final ActionService actionService;

    private final UserService userService;

    @Autowired
    public ActionController(ActionService actionService, UserService userService) {
        this.actionService = actionService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("get-recent-10-actions")
    public Map getRecent10Actions(@RequestParam String email) {
        Map result = new HashMap();
        try {
            result.put("actions", actionService.getTop10(userService.getByEmail(email)));
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
            result.put("actions", actionService.getTop5(userService.getByEmail(email)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }


}
