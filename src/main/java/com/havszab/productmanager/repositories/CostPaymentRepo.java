package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.CostPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostPaymentRepo extends JpaRepository<CostPayment, Long> {


}
