package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {


}
