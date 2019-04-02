package com.havszab.projectmanager.repositories;

import com.havszab.projectmanager.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByProductName(String name);
}
