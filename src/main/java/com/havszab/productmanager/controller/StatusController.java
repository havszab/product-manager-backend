package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.enums.Status;
import com.havszab.productmanager.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class StatusController {

    @Autowired
    ProductRepo productRepo;

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

    @PostMapping("/set-product-status")
    public Map setProductStatus(@RequestBody Map product) {
        Map response = new HashMap();
        try {
            Long id = (long) (int) product.get("id");
            Status newStatus = Status.valueOf((String) product.get("status"));
            Product prodToUpdate = productRepo.getOne(id);
            if (prodToUpdate != null) {
                prodToUpdate.setStatus(newStatus);
                productRepo.save(prodToUpdate);
            }
            response.put("success", true);
            response.put("message", "Status change was successful!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Status change was not successful!");
        }
        return response;
    }
}
