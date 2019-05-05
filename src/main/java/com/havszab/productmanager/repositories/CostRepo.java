package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Cost;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface CostRepo extends JpaRepository<Cost, Long> {

    List<Cost> getAllByOwner(User owner);

    @Query(value =
                    "SELECT sum(c.cost) as sum_costs, c.type FROM cost AS c " +
                    "WHERE owner_id=?1 " +
                    "GROUP BY c.type; ", nativeQuery = true)
    List<Object> getCostSumsByTypes(Long ownerId);

    @Query(value =
            "SELECT cost, name FROM cost " +
                    "WHERE owner_id=?1 AND type='ANNUAL' " +
                    "GROUP BY name, cost " +
                    "ORDER BY cost DESC LIMIT 5; ", nativeQuery = true)
    List<Object> getTop5AnnualCostByOwner(Long ownerId);

    @Query(value =
                    "SELECT cost, name FROM cost " +
                    "WHERE owner_id=?1 AND type='MONTHLY' " +
                    "GROUP BY cost, name " +
                    "ORDER BY cost DESC LIMIT 5; ", nativeQuery = true)
    List<Object> getTop5MonthlyCostByOwner(Long ownerId);

    @Query(value =
            "SELECT cost, name FROM cost " +
                    "WHERE owner_id=?1 AND type='WEEKLY' " +
                    "GROUP BY name, cost " +
                    "ORDER BY cost DESC LIMIT 5; ", nativeQuery = true)
    List<Object> getTop5WeeklyCostByOwner(Long ownerId);

    @Query(value =
            "SELECT cost, name FROM cost " +
                    "WHERE owner_id=?1 AND type='OTHER' " +
                    "GROUP BY name, cost " +
                    "ORDER BY cost DESC LIMIT 5; ", nativeQuery = true)
    List<Object> getTop5OtherCostByOwner(Long ownerId);

    @Query(value =
            "SELECT DISTINCT c.name, sum(c.cost) AS amount FROM cost_payment as cp " +
            "JOIN cost c on cp.cost_id = c.id " +
            "WHERE c.owner_id = ?3 AND cp.date BETWEEN ?1 AND ?2 " +
            "GROUP BY c.name ; ", nativeQuery = true)
    List<Map> getCostsNamesAndAmountsPaidInSelectedYear(Date from, Date to, User user);

    List<Cost> findAllByOwnerOrderByIdDesc(User owner);
}
