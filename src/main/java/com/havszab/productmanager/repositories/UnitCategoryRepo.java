package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.ProductCategory;
import com.havszab.productmanager.model.UnitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitCategoryRepo extends JpaRepository<UnitCategory, Long> {

    UnitCategory findByUnitName(String name);

    List<UnitCategory> findAllByUnitNameIsNotNullOrderByUnitName();
 }
