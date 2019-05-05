package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.Cost;
import com.havszab.productmanager.model.enums.CostType;
import com.havszab.productmanager.service.CostService;
import com.havszab.productmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class CostController {

    private final CostService costService;

    private final UserService userService;

    @Autowired
    public CostController(CostService costService, UserService userService) {
        this.costService = costService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("/get-costs")
    public Map getCosts(@RequestParam String email) {
        Map response = new HashMap();
        response.put("costs", costService.getAll(userService.getByEmail(email)));
        return response;
    }

    @PostMapping("/add-cost")
    public Map addCost(@RequestBody Map costData) {
        Map response = new HashMap();
        try {
            String title = (String) costData.get("name");
            CostType type = CostType.valueOf((String) costData.get("type"));
            Double cost = Double.parseDouble((String) costData.get("cost"));
            String email = (String) costData.get("email");

            costService.save(new Cost(title, type, cost, userService.getByEmail(email)));
            response.put("success", true);
            response.put("message", "Cost registered successfully!");
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Cost registration was unsuccessful!");
        }
        return response;
    }

    @PostMapping("set-cost")
    public Map setCost(@RequestBody Map costData) {
        Map response = new HashMap();
        try {
            costService.set(costData);
            response.put("success", true);
            response.put("message", "Cost set successfully!");
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", true);
            response.put("message", "Could not edit cost!");
        }
        return response;
    }

    @PostMapping("mark-cost-as-paid")
    public Map marCostAsPaid(@RequestBody Map request) {
        Map response = new HashMap();
        try {
            Cost cost =  costService.get((long) (int) request.get("id"));
            costService.pay(userService.getByEmail((String) request.get("email")), cost);

            response.put("success", true);
            response.put("message", cost.getName() + " marked as payed. Amount: " + cost.getCost() + " HUF");
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("message", "Could not mark this cost as paid, try again later!");

        }
        return response;
    }

    @GetMapping("get-cost-types")
    public Map getCostTypes() {
        Map response = new HashMap();
        response.put("types", CostType.values());
        return response;
    }

    @GetMapping("get-cost-sums-by-type")
    public Map getCostSumsByTypes(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("sums", costService.getSumsByTypes(userService.getByEmail(email)));
        } catch (Exception e) {
            response.put("sums", e);
            return response;
        }
        return response;
    }


    @GetMapping("get-top5-annual")
    public Map getTop5AnnualCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costService.getTop5Annual(userService.getByEmail(email)));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }


    @GetMapping("get-top5-monthly")
    public Map getTop5MonthlyCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costService.getTop5Monthly(userService.getByEmail(email)));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }


    @GetMapping("get-top5-weekly")
    public Map getTop5WeeklyCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costService.getTop5Weekly(userService.getByEmail(email)));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }


    @GetMapping("get-top5-other")
    public Map getTop5OtherCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costService.getTop5Other(userService.getByEmail(email)));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }

    @GetMapping("get-cost-amounts")
    public Map getCostAmounts(@RequestParam int year, @RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costService.getCostsByYear(year, userService.getByEmail(email)));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }

}
