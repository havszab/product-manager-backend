package com.havszab.productmanager.controller;

import com.havszab.productmanager.model.Employee;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.service.EmployeeService;
import com.havszab.productmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

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
            Double salary = Double.parseDouble((String) employee.get("salary"));
            User user = userService.getByEmail(ownerEmail);

            employeeService.saveAndCreateCost(new Employee(
                    firstName,
                    lastName,
                    email,
                    salary,
                    phone,
                    position,
                    user
            ));

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
        try {
            response.put("success", true);
            response.put("employees", employeeService.getAllByOwner(userService.getByEmail(email)));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Could not fetch employees!");
        }
        return response;
    }
}
