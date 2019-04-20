package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Action;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepo extends JpaRepository<Action, Long> {

    List<Action> getTop10ByOwnerOrderByDateDesc(User owner);
}
