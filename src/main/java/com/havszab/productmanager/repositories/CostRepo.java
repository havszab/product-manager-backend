package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepo extends JpaRepository<Cost, Long> {

}
