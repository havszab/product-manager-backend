package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.SoldProduct;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public interface SoldProductRepo extends JpaRepository<SoldProduct, Long> {

    @Query(value =
            "SELECT SUM(profit) AS product_profit,  product_name FROM product AS p " +
                    "JOIN product_categories pc ON p.product_category_id = pc.id " +
                    "WHERE p.selling_date>?1 AND p.selling_date<?2 " +
                    "GROUP BY p.product_category_id, product_name " +
                    "ORDER BY product_name DESC", nativeQuery = true)
    List<Object> getProfitSumOfProductCategories(Date monthFrom, Date monthTo);

    @Query(value =
            "SELECT p.*, " +
                    "product_name, " +
                    "unit_name " +
                    "FROM product as p " +
                    "JOIN product_categories as pc ON p.product_category_id = pc.id " +
                    "JOIN unit_categories as uc ON p.unit_category_id = uc.id " +
                    "JOIN sales_products s2 on p.id = s2.products_id " +
                    "JOIN sales ON s2.sales_id = sales.id " +
                    "WHERE sales.owner_id=?3 AND p.selling_date BETWEEN ?1 AND ?2 " +
                    "ORDER BY selling_date DESC ", nativeQuery = true)
    List<Map> getMonthlySalesByOwner(Date monthFrom, Date monthTo, Long ownerId);

    @Query(value =
            "SELECT (SUM(profit)/(SELECT SUM(profit) FROM product) * 100) AS profit_perc, c2.product_name " +
                    "FROM product " +
                    "JOIN product_categories c2 on product.product_category_id = c2.id " +
                    "GROUP BY c2.product_name " +
                    "ORDER BY c2.product_name DESC " +
                    "LIMIT 5; ", nativeQuery = true)
    List<Object> getProfitPercentPerProductCategory();

    @Query(value =
            "SELECT SUM(p.profit) AS sum_profit, extract(day from selling_date) as day  " +
                    "FROM product AS p " +
                    "JOIN sales_products s2 on p.id = s2.products_id " +
                    "JOIN sales ON s2.sales_id = sales.id " +
                    "WHERE sales.owner_id = ?3 " +
                    "  AND p.selling_date BETWEEN ?1 AND ?2 " +
                    "GROUP BY day " +
                    "ORDER BY day ;", nativeQuery = true)
    List<Object> getSumProfitByDay(Date dateFrom, Date dateTo, User user);

    @Query(value =
            "SELECT SUM (p.selling_price) AS income, extract(day from selling_date) as day " +
                    "FROM product AS p " +
                    "JOIN sales_products s2 on p.id = s2.products_id " +
                    "JOIN sales ON s2.sales_id = sales.id " +
                    "WHERE sales.owner_id = ?3 " +
                    "  AND p.selling_date BETWEEN ?1 AND ?2 " +
                    "GROUP BY day " +
                    "ORDER BY day; ", nativeQuery = true)
    List<Object> getSumIncomeByDay(Date dateFrom, Date dateTo, User user);


}
