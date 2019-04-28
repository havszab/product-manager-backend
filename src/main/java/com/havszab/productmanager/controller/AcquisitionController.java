package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.model.enums.ActionColor;
import com.havszab.productmanager.model.enums.Status;
import com.havszab.productmanager.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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

    @Autowired
    ActionRepo actionRepo;

    private final String logBase = new Date().toString() + "[CONTROLLER REPORT: " + this.getClass() + "] - ";

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
    @GetMapping("/get-acquisition")
    public Map getAcquisitionByUser(@RequestParam String email) {
        Map response = new HashMap();
        try {
            User user = userRepo.findByEmail(email);
            Acquisition acquisition = acquisitionRepo.findAcquisitionByOwner(user);
            response.put("success", true);
            response.put("acquisition", acquisition);
        } catch (Exception e) {
            System.out.println(logBase + e);
            response.put("success", false);
        }
        return response;
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

            int numOfItems = toFinish.getProducts().size();
            acquisitionRepo.removeAcquisitionByOwner(user);
            Acquisition emptyAcquisition = new Acquisition(user);
            acquisitionRepo.save(emptyAcquisition);
            user.setAcquisition(emptyAcquisition);
            userRepo.save(user);
            System.out.println(logBase + "Acquisition finished");
            actionRepo.save(new Action(numOfItems + " products delivered to stock", new Date(), user, ActionColor.BLUE));
            return "Acquisition finished";
        } catch (Exception e) {
            System.out.println(e);
            result.put("success", false);
        }
        System.out.println(logBase + "Acquisition could not finish");
        return "Acquisition could not finish";
    }

    @CrossOrigin
    @PostMapping("finish-selected-items")
    public Map finishSelected(@RequestBody Map requestData) {
        Map response = new HashMap();
        try {
            User user = userRepo.findByEmail((String) requestData.get("email"));
            List<LinkedHashMap> rawProducts = (ArrayList) requestData.get("products");
            Set<Product> products = mapRequestDataToProductSet(rawProducts);

            Acquisition acquisition = acquisitionRepo.findAcquisitionByOwner(user);
            Set<Product> acqProducts = acquisition.getProducts();

            acqProducts.removeAll(products);
            acquisition.setProducts(acqProducts);

            Stock stock = stockRepo.findStockByOwner(user);
            Set<Product> stockProducts = stock.getProducts();
            stockProducts.addAll(products);
            stock.setProducts(stockProducts);

            acquisitionRepo.save(acquisition);
            stockRepo.save(stock);

            actionRepo.save(new Action(products.size() + " products delivered to stock", new Date(), user, ActionColor.BLUE));

            response.put("success", true);
            response.put("message", "Items moved to stock successfully");
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Could not finish acquisition with the selected items.");
        }
        return response;
    }

    @CrossOrigin
    @PostMapping("remove-selected-items")
    public Map removeSelectedItems(@RequestBody Map requestData) {
        Map response = new HashMap();
        try {
            User user = userRepo.findByEmail((String) requestData.get("email"));
            List<LinkedHashMap> rawProducts = (ArrayList) requestData.get("products");
            Set<Product> products = mapRequestDataToProductSet(rawProducts);

            Acquisition acquisition = acquisitionRepo.findAcquisitionByOwner(user);
            Set<Product> acqProducts = acquisition.getProducts();

            acqProducts.removeAll(products);
            acquisition.setProducts(acqProducts);

            acquisitionRepo.save(acquisition);

            response.put("success", true);
            response.put("message", "Items removed successfully");
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Could not remove selected items.");
        }
        return response;
    }

    private Set<Product> mapRequestDataToProductSet(List<LinkedHashMap> unmappedList) {
        Set<Product> products = new HashSet<>();
        for (LinkedHashMap prod : unmappedList) {
            products.add(productRepo.getOne((long) (int) prod.get("id")));
        }
        return products;
    }


}
