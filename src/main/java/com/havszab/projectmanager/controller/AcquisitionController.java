package com.havszab.projectmanager.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class AcquisitionController {

    @GetMapping("/")
    public String hello() {
        return "HELLO";
    }

}
