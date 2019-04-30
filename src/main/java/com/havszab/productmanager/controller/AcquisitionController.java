package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.model.enums.ActionColor;
import com.havszab.productmanager.repositories.*;
import com.havszab.productmanager.service.*;
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

    @Autowired
    ItemPaymentService itemPaymentService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    UnitCategoryService unitCategoryService;

    @Autowired
    UserService userService;

    @Autowired
    AcquisitionService acquisitionService;

    private final String logBase = new Date().toString() + "[CONTROLLER REPORT: " + this.getClass() + "] - ";

    @CrossOrigin
    @PostMapping("/add-item")
    public Map saveProduct(@RequestBody Map prodToSave) {
        Map response = new HashMap();
        try {
            User user = userService.getUserByEmail((String) prodToSave.get("email"));

            String productCategoryName = (String) prodToSave.get("name");
            Long itemPrice = Long.parseLong((String) prodToSave.get("price"));
            Long quantity = Long.parseLong((String) prodToSave.get("quantity"));
            String description = (String) prodToSave.get("description");

            ProductCategory productCategory = productCategoryService.getByName(productCategoryName);
            UnitCategory unitCategory = unitCategoryService.getUnitCategory((String) prodToSave.get("unit"));
            Product product = productService.persistAndGetProduct(new Product(
                    productCategory,
                    unitCategory,
                    quantity,
                    itemPrice,
                    description));
            acquisitionService.addProduct(product, user);

            response.put("success", true);
            response.put("message", "Item " + productCategoryName + " successfully added to acquisition!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Couldn\'t add item to acquisition!");
        }
        return response;
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
    public Map finishAcquisition(@RequestBody Map userData) {
        Map response = new HashMap();
        try {
            String email = (String) userData.get("email");
            User user = userRepo.findByEmail(email);

            Acquisition toFinish = user.getAcquisition();
            Stock stock = user.getStock();
            HashSet<Product> productsToAdd = new HashSet<>(toFinish.getProducts());
            stock.getProducts().addAll(productsToAdd);

            productService.setProductsStatusToIN_STOCK(productsToAdd);

            user.setStock(stock);

            int numOfItems = toFinish.getProducts().size();
            acquisitionRepo.removeAcquisitionByOwner(user);
            Acquisition emptyAcquisition = new Acquisition(user);
            acquisitionRepo.save(emptyAcquisition);
            user.setAcquisition(emptyAcquisition);
            userRepo.save(user);
            System.out.println(logBase + "Acquisition finished!");
            actionRepo.save(new Action(numOfItems + " products delivered to stock!", new Date(), user, ActionColor.BLUE));
            response.put("success", true);
            response.put("message", numOfItems + " products delivered to stock!");
        } catch (Exception e) {
            System.out.println(logBase + e);
            response.put("success", false);
            response.put("message", "Acquisition finish failed!");
        }
        return response;
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

            itemPaymentService.persistAcquiredItemsAsPayment(products, user);
            productService.setProductsStatusToIN_STOCK(products);

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
