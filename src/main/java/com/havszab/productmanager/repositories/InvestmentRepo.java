package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Investment;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepo extends JpaRepository<Investment, Long> {


    List<Investment> getAllByOwnerOrderByIdDesc (User user);
 }
