package com.havszab.productmanager.service;

import com.havszab.productmanager.model.User;
import com.havszab.productmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}

