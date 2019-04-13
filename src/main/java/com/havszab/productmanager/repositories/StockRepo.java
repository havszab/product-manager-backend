package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Stock;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {

    Stock findStockByOwner(User owner);
}
