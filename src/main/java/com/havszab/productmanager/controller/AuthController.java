package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.User;
import com.havszab.productmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/auth/login")
    public Map login(@RequestBody Map userData) {
        Map result = new HashMap();
        try {
            User user = userService.getByEmail((String) userData.get("email"));
            if (user.getPassword().equals(userData.get("password"))) {
                result.put("success", true);
                result.put("user", user);
            } else
                result.put("success", false);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }
}
