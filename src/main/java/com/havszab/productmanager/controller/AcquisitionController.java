package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
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

    @Autowired
    StockRepo stockRepo;

    private final String logBase = "[CONTROLLER LOG: " + this.getClass() +  "] - ";

    @CrossOrigin
    @GetMapping("/products")
    public Map getAllProducts() {
        Map result = new HashMap();
        result.put("products", productRepo.findAll());
        System.out.println(logBase + "All products provided.");
        return result;
    }

    @CrossOrigin
    @PostMapping("/save")
    public String saveProduct(@RequestBody Map prodToSave) {
        String email = (String) prodToSave.get("email");
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
            Product product = new Product(cat, unitCategory, quantity, price, description);
            productRepo.save(product);
            User user = userRepo.findByEmail(email);
            Acquisition acquisition = acquisitionRepo.findAcquisitionByOwner(user);
            Set<Product> currentProducts = acquisition.getProducts();
            currentProducts.add(product);
            acquisitionRepo.save(acquisition);
            System.out.println(logBase + "Product saved to acquisition");
        } catch (Exception e) {
            try {
                System.out.println(logBase + "Error during adding item to acquisition. Method: " + this.getClass().getMethod("saveProduct", Map.class) + " Error: " + e);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
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
        System.out.println(logBase + "Acquisition request fulfilled for user: " + email);
        return result;
    }

    @CrossOrigin
    @GetMapping("/get-all-status")
    public Map getAllStatus() {
        Map result = new HashMap();
        result.put("statuses", Status.values());
        System.out.println(logBase + "Statuses provided");
        return result;
    }

    @CrossOrigin
    @PostMapping("/set-product-status")
    public String setProductStatus(@RequestBody Map product) {
        Long id = (long) (int) product.get("id");
        Status newStatus = Status.valueOf((String) product.get("status"));
        Product prodToUpdate = productRepo.findById(id)
                .orElse(null);
        if (prodToUpdate != null) {
            prodToUpdate.setStatus(newStatus);
            productRepo.save(prodToUpdate);
            System.out.println(logBase + "Status changed");
            return "Status changed";
        }
        System.out.println(logBase + "Status change wasn't successful");
        return "Status change wasn't successful";

    }

    @CrossOrigin
    @PostMapping("/finish-acquisition")
    public String finishAcquisition(@RequestBody Map userData) {
        Map result = new HashMap();
        try {
            String email = (String) userData.get("email");
            User user = userRepo.findByEmail(email);

            Acquisition toFinish = user.getAcquisition();
            Stock stock = user.getStock();
            HashSet<Product> productsToAdd = new HashSet<>(toFinish.getProducts());
            stock.getProducts().addAll(productsToAdd);

            for (Product product : stock.getProducts()) {
                product.setStatus(Status.IN_STOCK);
            }
            user.setStock(stock);

            acquisitionRepo.removeAcquisitionByOwner(user);
            Acquisition emptyAcquisition = new Acquisition(user);
            acquisitionRepo.save(emptyAcquisition);
            user.setAcquisition(emptyAcquisition);
            userRepo.save(user);
            System.out.println(logBase + "Acquisition finished");
            return "Acquisition finished";
        } catch (Exception e) {
            System.out.println(e);
            result.put("success", false);
        }
        System.out.println(logBase + "Acquisition could not finish");
        return "Acquisition could not finish";
    }
}
