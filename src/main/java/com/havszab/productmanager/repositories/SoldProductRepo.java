package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldProductRepo extends JpaRepository<SoldProduct, Long> {


}
