package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.enums.Status;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class StatusController {

    @CrossOrigin
    @GetMapping("/get-all-status")
    public Map getAllStatus() {
        Map result = new HashMap();
        try {
            result.put("statuses", Status.values());
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }

}
