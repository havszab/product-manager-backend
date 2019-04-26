package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByProductName(String name);

    List<ProductCategory> findAllByProductNameIsNotNullOrderByProductName();
}
