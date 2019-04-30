package com.havszab.productmanager.service;

import com.havszab.productmanager.model.UnitCategory;
import com.havszab.productmanager.repositories.UnitCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitCategoryService {

    @Autowired
    UnitCategoryRepo unitCategoryRepo;

    public UnitCategory getUnitCategory(String name) {
        return unitCategoryRepo.findByUnitName(name);
    }
}
