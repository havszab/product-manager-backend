package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.model.enums.ActionColor;
import com.havszab.productmanager.repositories.*;
import com.havszab.productmanager.service.SalesService;
import com.havszab.productmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@CrossOrigin
@RestController
public class SalesController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    SoldProductRepo soldProductRepo;

    @Autowired
    SalesRepo salesRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    StockRepo stockRepo;

    @Autowired
    ActionRepo actionRepo;

    @Autowired
    private SalesService salesService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("get-sales")
    public Map getAllSales(@RequestBody Map userData) {
        Map result = new HashMap();
        User user = userRepo.findByEmail((String) userData.get("email"));
        result.put("sales", salesRepo.findSalesByOwner(user));
        return result;
    }

    @CrossOrigin
    @PostMapping("sell-item")
    public Map sellItem(@RequestBody Map request) {
        Map response = new HashMap();
        try {
            Long productId = (long) (int) request.get("prodToSell");
            Product product = productRepo.findById(productId)
                    .orElse(null);
            Long income = (long) (int) request.get("income");
            double profit = request.get("profit") instanceof Integer ? (double) (int) request.get("profit") : (double) request.get("profit");
            Long quantity = (long) (int) request.get("quantToSell");
            double value = request.get("value") instanceof Integer ? (double) (int) request.get("value") : (double) request.get("value");
            User user = userRepo.findByEmail((String) request.get("email"));
            Sales sales = salesRepo.findSalesByOwner(user);

            SoldProduct soldProduct = new SoldProduct(
                    product.getProductCategory(),
                    product.getUnitCategory(),
                    quantity,
                    value,
                    "",
                    new Date(),
                    income,
                    profit);
            actionRepo.save(new Action(
                    quantity +
                            " " +
                            product.getUnitCategory().getUnitName() +
                            " of " +
                            product.getProductCategory().getProductName() +
                            " sold for " +
                            (int) value + "HUF",
                    new Date(), user, ActionColor.GREEN));
            soldProductRepo.save(soldProduct);

            if (product.getQuantity() == quantity) {
                Set<Product> stockProducts = stockRepo.findStockByOwner(user).getProducts();
                stockProducts.remove(product);
                stockRepo.save(stockRepo.findStockByOwner(user));
            } else {
                product.setQuantity(product.getQuantity() - quantity);
                product.setItemPrice(product.getItemPrice() - value);
                productRepo.save(product);
            }

            sales.getProducts().add(soldProduct);
            salesRepo.save(sales);

            response.put("success", true);
            response.put("message", quantity +
                    " " +
                    product.getUnitCategory().getUnitName() +
                    " of " +
                    product.getProductCategory().getProductName() +
                    " sold for " +
                    (int) value + "HUF.");

        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Could not sell item successfully!");
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("get-income-per-days")
    public Map getIncomePerDays(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam String email) {
        Map response = new HashMap();
        try {
            Date from = new Date(Long.parseLong(dateFrom));
            Date to = new Date(Long.parseLong(dateTo));

            response.put("success", true);
            response.put("incomePerDays", soldProductRepo.getSumIncomeByDay(from, to, userService.getByEmail(email)));
        } catch (Exception e) {
            System.out.println(e);

            response.put("success", false);
            response.put("error", e);
            throw e;
        }
        return response;
    }

    @GetMapping("get-income")
    public Map getIncomeByYear (@RequestParam int year, @RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("success", true);
            response.put("income", salesService.getIncomeByYear(year, userService.getByEmail(email)));
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", e);
            throw e;
        }
        return response;
    }

    @GetMapping("get-incomes-of-years")
    public Map getIncomesOfYears ( @RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("success", true);
            response.put("incomes", salesService.getIncomesOfYears(userService.getByEmail(email)));
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", e);
            throw e;
        }
        return response;
    }
}
