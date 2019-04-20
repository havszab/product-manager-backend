package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.Sales;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Long> {

    Sales findSalesByOwner(User owner);
}
