package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Product;
import com.havszab.productmanager.model.Sales;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Long> {

    Sales findSalesByOwner(User owner);

    @Query(value =
            "SELECT sum(p.selling_price) AS product_income FROM product AS p " +
            "JOIN sales_products s2 on p.id = s2.products_id " +
            "JOIN sales s3 on s2.sales_id = s3.id " +
            "WHERE s3.owner_id = ?3 AND p.selling_date BETWEEN ?1 AND ?2 ;", nativeQuery = true)
    Map getIncomeByYear(Date from, Date to, Long id);

    @Query(value =
            "SELECT extract(year from selling_date) as year, sum(p.selling_price) AS product_income " +
            "FROM product AS p " +
            "       JOIN sales_products s2 on p.id = s2.products_id " +
            "       JOIN sales s3 on s2.sales_id = s3.id " +
            "WHERE s3.owner_id = ?1 " +
            "GROUP BY year " +
            "ORDER BY year ; ", nativeQuery = true)
    List<Map> getIncomesOfYears(User user);

    @Query(value =
            "SELECT extract(year from selling_date) as year, sum(p.profit) AS profit " +
                    "FROM product AS p " +
                    "       JOIN sales_products s2 on p.id = s2.products_id " +
                    "       JOIN sales s3 on s2.sales_id = s3.id " +
                    "WHERE s3.owner_id = ?1 " +
                    "GROUP BY year " +
                    "ORDER BY year ; ", nativeQuery = true)
    List<Map> getProfitsOfYears(User user);

    @Query(value =
            "    SELECT COUNT(p.id) AS count " +
            "    FROM product AS p " +
            "           JOIN sales_products s2 on p.id = s2.products_id " +
            "           JOIN sales s3 on s2.sales_id = s3.id " +
            "    WHERE s3.owner_id = ?1 ; ", nativeQuery = true)
    Map getSoldProductsCount(User user);
}
