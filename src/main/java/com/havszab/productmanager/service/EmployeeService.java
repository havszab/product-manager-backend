package com.havszab.productmanager.service;

import com.havszab.productmanager.model.Cost;
import com.havszab.productmanager.model.Employee;
import com.havszab.productmanager.model.User;
import com.havszab.productmanager.model.enums.CostType;
import com.havszab.productmanager.repositories.CostRepo;
import com.havszab.productmanager.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    CostRepo costRepo;

    public void saveAndCreateCost(Employee employee) {
        employeeRepo.save(employee);
        costRepo.save(new Cost(employee.getFirstName()+ " " + employee.getLastName() + "'s salary", CostType.MONTHLY, employee.getSalary(), employee.getOwner()));
    }

    public List<Employee> getAllByOwner (User user) {
        return employeeRepo.getEmployeesByOwner(user);
    }
}
