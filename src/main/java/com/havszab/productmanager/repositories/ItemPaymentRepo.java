package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.ItemPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPaymentRepo extends JpaRepository<ItemPayment, Long> {

}
