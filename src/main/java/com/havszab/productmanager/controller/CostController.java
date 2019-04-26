package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.Action;
import com.havszab.productmanager.model.Cost;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.model.enums.ActionColor;
import com.havszab.productmanager.model.enums.CostType;
import com.havszab.productmanager.repositories.ActionRepo;
import com.havszab.productmanager.repositories.CostRepo;
import com.havszab.productmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CostController {

    @Autowired
    CostRepo costRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ActionRepo actionRepo;

    @CrossOrigin
    @GetMapping("/get-costs")
    public Map getCosts(@RequestParam String email) {
        Map response = new HashMap();
        User user = userRepo.findByEmail(email);
        response.put("costs", costRepo.findAllByOwnerOrderByIdDesc(user));
        return response;
    }

    @CrossOrigin
    @PostMapping("/add-cost")
    public String addCost(@RequestBody Map costData) {
        try {
            String title = (String) costData.get("name");
            CostType type = CostType.valueOf((String) costData.get("type"));
            Double cost = Double.parseDouble((String) costData.get("cost"));
            String email = (String) costData.get("email");
            costRepo.save(new Cost(title, type, cost, userRepo.findByEmail(email)));
        } catch (Exception e) {
            System.out.println(e);
            return "Couldn't save cost.";
        }
        return "Cost save successfully";
    }

    @CrossOrigin
    @PostMapping("set-cost")
    public String setCost(@RequestBody Map costData) {
        try {
            Long id = (long) (int) costData.get("id");
            String title = (String) costData.get("name");
            CostType type = (CostType) costData.get("type");
            Double cost = Double.parseDouble((String) costData.get("cost"));
            String email = (String) costData.get("email");
            String dateInLong = (String) costData.get("date");
            Date date = new Date(Long.parseLong(dateInLong));
            Cost costToSet = costRepo.getOne(id);
            costToSet.setName(title);
            costToSet.setType(type);
            costToSet.setCost(cost);
            costToSet.setPayedLastDate(date);
            costRepo.save(costToSet);
        } catch (Exception e) {
            System.out.println(e);
            return "Couldn't save cost.";
        }
        return "Cost save successfully";
    }

    @CrossOrigin
    @PostMapping("mark-cost-as-paid")
    public String marCostAsPaid(@RequestBody Map request) {
        try {
            Long id = (long) (int) request.get("id");
            String email = (String) request.get("email");
            Cost paidCost = costRepo.getOne(id);
            paidCost.setPayedLastDate(new Date());
            costRepo.save(paidCost);
            actionRepo.save(new Action(
                    paidCost.getName() + "  payed. Amount: " + paidCost.getCost() + " HUF",
                    new Date(),
                    userRepo.findByEmail(email),
                    ActionColor.RED));
        } catch (Exception e) {
            System.out.println(e);
            return e.toString();
        }
        return "Cost marked as paid";
    }

    @CrossOrigin
    @GetMapping("get-cost-types")
    public Map getCostTypes() {
        Map response = new HashMap();
        response.put("types", CostType.values());
        return response;
    }

    @CrossOrigin
    @GetMapping("get-cost-sums-by-type")
    public Map getCostSumsByTypes(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("sums", costRepo.getCostSumsByTypes(userRepo.findByEmail(email).getId()));
        } catch (Exception e) {
            response.put("sums", e);
            return response;
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("get-top5-annual")
    public Map getTop5AnnualCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costRepo.getTop5AnnualCostByOwner(userRepo.findByEmail(email).getId()));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("get-top5-monthly")
    public Map getTop5MonthlyCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costRepo.getTop5MonthlyCostByOwner(userRepo.findByEmail(email).getId()));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("get-top5-weekly")
    public Map getTop5WeeklyCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costRepo.getTop5WeeklyCostByOwner(userRepo.findByEmail(email).getId()));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("get-top5-other")
    public Map getTop5OtherCost(@RequestParam String email) {
        Map response = new HashMap();
        try {
            response.put("costs", costRepo.getTop5OtherCostByOwner(userRepo.findByEmail(email).getId()));
            response.put("success", true);
        }catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
        }
        return response;
    }
}
