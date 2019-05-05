package com.havszab.productmanager.controller;

import com.havszab.productmanager.service.ItemPaymentService;
import com.havszab.productmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class ItemPaymentController {

    @Autowired
    ItemPaymentService itemPaymentService;

    @Autowired
    UserService userService;

    @GetMapping("get-product-costs")
    public Map getProductCostsOfSelectedYear(@RequestParam int year, @RequestParam String email) {
        Map response = new HashMap();
            response.put("productCostSum", itemPaymentService.getProductCostsOfSelectedYear(year, userService.getByEmail(email)));
        try {
            response.put("success", true);
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Could not provide product costs!");
        }
        return response;
    }

}
