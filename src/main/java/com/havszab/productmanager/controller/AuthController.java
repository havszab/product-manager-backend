package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class AuthController {

    @Autowired
    UserRepo userRepo;

    @CrossOrigin
    @PostMapping("/auth/login")
    public Map login(@RequestBody Map userData) {
        Map result = new HashMap();
        String name = (String) userData.get("email");
        String password = (String) userData.get("password");
        try {
            User user = userRepo.findByEmail(name);
            if (user.getPassword().equals(password)) {
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
