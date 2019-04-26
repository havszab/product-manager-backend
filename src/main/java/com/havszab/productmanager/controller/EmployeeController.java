package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.Cost;
import com.havszab.productmanager.model.Employee;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.model.enums.CostType;
import com.havszab.productmanager.repositories.CostRepo;
import com.havszab.productmanager.repositories.EmployeeRepo;
import com.havszab.productmanager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CostRepo costRepo;

    @CrossOrigin
    @PostMapping("save-employee")
    public String saveEmployee(@RequestBody Map employee) {
        try {
            String ownerEmail = (String) employee.get("ownerEmail");
            String firstName = (String) employee.get("firstName");
            String lastName = (String) employee.get("lastName");
            String phone = (String) employee.get("phone");
            String email = (String) employee.get("email");
            String position = (String) employee.get("position");
            Double salary = Double.parseDouble( (String) employee.get("salary"));
            User user = userRepo.findByEmail(ownerEmail);

            employeeRepo.save(new Employee(
                    firstName,
                    lastName,
                    email,
                    salary,
                    phone,
                    position,
                    user
            ));

            costRepo.save(new Cost(firstName + " " + lastName + "'s salary", CostType.MONTHLY, salary, user));
        } catch (Exception e) {
            System.out.println(e);
            return "Could not create employee with the given data";
        }
        return "Employee saved successfully";
    }

    @CrossOrigin
    @GetMapping("get-employees")
    public Map getEmployees(@RequestParam String email) {
        Map response = new HashMap();
        response.put("employees", employeeRepo.getEmployeesByOwner(userRepo.findByEmail(email)));
        return response;
    }
}
