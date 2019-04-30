package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.*;
import com.havszab.productmanager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class AcquisitionController {

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
            response.put("success", true);
            response.put("acquisition", acquisitionService.get(userService.getUserByEmail(email)));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Could fulfill acquisition request: " + e);
        }
        return response;
    }

    @CrossOrigin
    @PostMapping("/finish-acquisition")
    public Map finishAcquisition(@RequestBody Map userData) {
        Map response = new HashMap();
        try {
            acquisitionService.moveAllItemsToStock(userService.getUserByEmail((String) userData.get("email")));
            response.put("success", true);
            response.put("message", "Products delivered to stock!");
        } catch (Exception e) {
            System.out.println(e);
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
            acquisitionService.moveSelectedItemsToStock(userService.getUserByEmail((String) requestData.get("email")), mapRequestDataToProductSet( (ArrayList) requestData.get("products")));
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

            acquisitionService.removeItems(
                    userService.getUserByEmail((String) requestData.get("email")),
                    mapRequestDataToProductSet((ArrayList) requestData.get("products"))
            );
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
            products.add(productService.getById((long) (int) prod.get("id")));
        }
        return products;
    }
}
