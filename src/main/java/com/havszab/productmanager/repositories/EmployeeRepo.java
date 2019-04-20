package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Employee;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> getEmployeesByOwner(User owner);

}
