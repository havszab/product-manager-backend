package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.model.enums.ActionColor;
import com.havszab.productmanager.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

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
    public String sellItem(@RequestBody Map request) {
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
                productRepo.delete(product);
            } else {
                product.setQuantity(product.getQuantity() - quantity);
                product.setItemPrice(product.getItemPrice() - value);
                productRepo.save(product);
            }

            sales.getProducts().add(soldProduct);
            salesRepo.save(sales);

        } catch (Exception e) {
            System.out.println(e);
            return "fail";
        }
        return "success";
    }

}