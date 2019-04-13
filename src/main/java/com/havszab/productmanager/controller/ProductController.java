package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.ProductCategory;
import com.havszab.productmanager.model.UnitCategory;
import com.havszab.productmanager.repositories.ProductCategoryRepo;
import com.havszab.productmanager.repositories.UnitCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    ProductCategoryRepo productCategoryRepo;

    @Autowired
    UnitCategoryRepo unitCategoryRepo;

    @CrossOrigin
    @PostMapping("/get-product-categories")
    public Map getProductCategories() {
        Map result = new HashMap();
        result.put("productCategories", productCategoryRepo.findAll());
        return result;
    }

    @CrossOrigin
    @PostMapping("/get-unit-categories")
    public Map getUnitCategories() {
        Map result = new HashMap();
        result.put("unitCategories", unitCategoryRepo.findAll());
        return result;
    }

    @CrossOrigin
    @PostMapping("/add-product-category")
    public String addProductCategory(@RequestBody Map prodCat) {
        String prodName = (String) prodCat.get("name");
        ProductCategory productCategory = new ProductCategory(prodName);
        try {
            productCategoryRepo.save(productCategory);
        } catch (Exception e) {
            System.out.println(e);
            return "Category save was unsuccessful";
        }
        return prodName + " successfully saved";
    }

    @CrossOrigin
    @PostMapping("/add-unit-category")
    public String addUnitCategory(@RequestBody Map unitCat) {
        String unitName = (String) unitCat.get("name");
        UnitCategory unitCategory = new UnitCategory(unitName);
        try {
            unitCategoryRepo.save(unitCategory);
        } catch (Exception e) {
            System.out.println(e);
            return "Category save was unsuccessful";
        }
        return unitName + " successfully saved";
    }
}
