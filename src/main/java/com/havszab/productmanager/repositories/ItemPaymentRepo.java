package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.ItemPayment;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ItemPaymentRepo extends JpaRepository<ItemPayment, Long> {


    @Query(value =
            "SELECT sum(p.item_price) AS product_costs FROM item_payment AS ip " +
            "JOIN product AS p ON ip.item_id = p.id " +
            "WHERE user_id = ?3 AND ip.date BETWEEN ?1 AND ?2 ; ", nativeQuery = true)
    Map getProductCostsOfSelectedYear(Date from, Date to, User user);
}
