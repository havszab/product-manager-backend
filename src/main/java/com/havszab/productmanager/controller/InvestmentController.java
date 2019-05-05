package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.Investment;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.service.InvestmentService;
import com.havszab.productmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private UserService userService;

    @GetMapping("get-all-investments")
    public Map getAllInvestment(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("investments", investmentService.getAll(userService.getByEmail(email)));
            response.put("success", true);
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Could not provide investments!");
        }
        return response;
    }

    @PostMapping("save-investment")
    public Map saveInvestment(@RequestBody Map requestData) {
        Map response = new HashMap();
        try {
            investmentService.save(new Investment(
                    (String) requestData.get("title"),
                    Long.parseLong((String) requestData.get("value")),
                    (String) requestData.get("description"),
                    userService.getByEmail((String) requestData.get("email")),
                    new Date()
            ));
            response.put("success", true);
            response.put("message", "Investment saved successfully!");
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Could not save investment!");
        }
        return response;
    }

    @PostMapping("edit-investment")
    public Map editInvestment(@RequestBody Map requestData) {
        Map response = new HashMap();
        try {
            investmentService.set(requestData);
            response.put("success", true);
            response.put("message", "Investment details successfully edited!");
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Investment data modification was unsuccessful!");
        }
        return response;
    }
}
