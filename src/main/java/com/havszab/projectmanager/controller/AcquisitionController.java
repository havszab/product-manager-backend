package com.havszab.projectmanager.controller;

import com.havszab.projectmanager.model.*;
import com.havszab.projectmanager.repositories.*;
import com.sun.xml.internal.xsom.impl.scd.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Component
public class AcquisitionController {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductCategoryRepo productCategoryRepo;

    @Autowired
    UnitCategoryRepo unitCategoryRepo;

    @Autowired
    AcquisitionRepo acquisitionRepo;

    @Autowired
    UserRepo userRepo;

    @CrossOrigin
    @GetMapping("/products")
    public Map getAllProducts() {
        Map result = new HashMap();
        result.put("products", productRepo.findAll());
        return result;
    }

    @CrossOrigin
    @PostMapping("/save")
    public String saveProduct(@RequestBody Map prodToSave) {
        String name = (String) prodToSave.get("name");
        Long price = Long.parseLong((String) prodToSave.get("price"));
        Long quantity = Long.parseLong((String) prodToSave.get("quantity"));
        String unit = (String) prodToSave.get("unit");
        String description = (String) prodToSave.get("description");
        ProductCategory cat = productCategoryRepo.findByProductName(name);
        UnitCategory unitCategory = unitCategoryRepo.findByUnitName(unit);

        if (cat == null)
            cat = new ProductCategory(name);
        if (unitCategory == null)
            unitCategory = new UnitCategory(unit);
        try {
            productCategoryRepo.save(cat);
            unitCategoryRepo.save(unitCategory);
            productRepo.save(new Product(cat, unitCategory, quantity, price, description));
        } catch (Exception e) {
            System.out.println(e);
            return "Couldn't save item";
        }
        return "Save successful";
    }

    @CrossOrigin
    @PostMapping("/get-acquisition")
    public Map getAcquisitionByUser(@RequestBody Map userData) {
        Map result = new HashMap();
        String email = (String) userData.get("email");
        User user = userRepo.findByEmail(email);
        Acquisition acquisition = acquisitionRepo.findAcquisitionByOwner(user);
        result.put("acquisition", acquisition);
        return result;
    }

}
