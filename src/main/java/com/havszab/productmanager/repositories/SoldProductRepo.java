package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface SoldProductRepo extends JpaRepository<SoldProduct, Long> {

    @Query(value =
            "SELECT SUM(profit) AS product_profit,  product_name FROM product AS p " +
            "JOIN product_categories pc ON p.product_category_id = pc.id " +
            "WHERE p.selling_date>?1 AND p.selling_date<?2 " +
            "GROUP BY p.product_category_id, product_name " +
            "ORDER BY product_profit DESC", nativeQuery = true)
    List<Object> getProfitSumOfProductCategories(Date dateFrom, Date dateTo);


    @Query(value =
            "SELECT (SUM(profit)/(SELECT SUM(profit) FROM product) * 100) AS profit_perc, c2.product_name " +
            "FROM product " +
            "JOIN product_categories c2 on product.product_category_id = c2.id " +
            "GROUP BY c2.product_name " +
            "ORDER BY profit_perc DESC " +
            "LIMIT 5; ", nativeQuery = true)
    List<Object> getProfitPercentPerProductCategory();
}
