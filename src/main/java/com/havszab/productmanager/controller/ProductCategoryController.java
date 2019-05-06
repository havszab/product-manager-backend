package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.ProductCategory;
import com.havszab.productmanager.repositories.ProductCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Repository
public class ProductCategoryController {

    @Autowired
    ProductCategoryRepo productCategoryRepo;

    @PostMapping("set-product-category")
    public Map setProductCategory(@RequestBody Map data) {
        Map response = new HashMap();
        try {
            Long catId = (long) (int) data.get("id");
            String newName = (String) data.get("name");
            ProductCategory cat = productCategoryRepo.getOne(catId);
            cat.setProductName(newName);
            response.put("success", true);
            response.put("message", "Category set successfully.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Couldn't set category");
        }
        return response;
    }

    @DeleteMapping("delete-product-category")
    public Map deleteProductCategory(@RequestParam int id) {
        Map response = new HashMap();
        try {
            Long catId = (long) id;
            productCategoryRepo.deleteById(catId);
            response.put("success", true);
            response.put("message", "Category deleted successfully.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Couldn't delete category");
        }
        return response;
    }
}
