package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.ProductCategory;
import com.havszab.productmanager.model.UnitCategory;
import com.havszab.productmanager.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
public class ProductController {

    @Autowired
    ProductCategoryRepo productCategoryRepo;

    @Autowired
    UnitCategoryRepo unitCategoryRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    SoldProductRepo soldProductRepo;

    @Autowired
    UserRepo userRepo;

    @CrossOrigin
    @PostMapping("/get-product-categories")
    public Map getProductCategories() {
        Map result = new HashMap();
        result.put("productCategories", productCategoryRepo.findAllByProductNameIsNotNullOrderByProductName());
        return result;
    }

    @CrossOrigin
    @PostMapping("/get-unit-categories")
    public Map getUnitCategories() {
        Map result = new HashMap();
        result.put("unitCategories", unitCategoryRepo.findAllByUnitNameIsNotNullOrderByUnitName());
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

    @CrossOrigin
    @GetMapping("/get-profit")
    public Map getProfit(@RequestParam String from, @RequestParam String to) {
        Date dateFrom = new Date(Long.parseLong(from));
        Date dateTo = new Date(Long.parseLong(to));
        Map result = new HashMap();
        result.put("profitsByCategories", soldProductRepo.getProfitSumOfProductCategories(dateFrom, dateTo));
        return result;
    }

    @CrossOrigin
    @GetMapping("get-profit-percent")
    public Map getProfitPercentPerProduct() {
        Map result = new HashMap();
        result.put("profitPercent", soldProductRepo.getProfitPercentPerProductCategory());
        return result;
    }

    @CrossOrigin
    @GetMapping("get-profit-per-days")
    public Map getProfitPerDays(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam String email) {
        Map response = new HashMap();
        try {
            LocalDate from = new Date(Long.parseLong(dateFrom)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate to = new Date(Long.parseLong(dateTo)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int beginningDay = from.getDayOfMonth();
            int endDay = to.getDayOfMonth();

            Long id = userRepo.findByEmail(email).getId();

            List<Object> result = new ArrayList<>();

            while (beginningDay != endDay + 1) {
                Date fromDay = new GregorianCalendar(from.getYear(), from.getMonthValue() - 1, beginningDay).getTime();
                Date toDay = new GregorianCalendar(from.getYear(), from.getMonthValue() - 1, beginningDay + 1).getTime();
                result.add(soldProductRepo.getSumProfitByDay(fromDay, toDay, id));
                beginningDay++;
            }
            response.put("success", true);
            response.put("profitPerDays", result);
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("error", e);
        }
        return response;
    }


}
