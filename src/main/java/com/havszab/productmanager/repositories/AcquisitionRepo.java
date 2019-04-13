package com.havszab.productmanager.repositories;

import com.havszab.productmanager.model.Acquisition;
import com.havszab.productmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AcquisitionRepo extends JpaRepository<Acquisition, Long> {

    Acquisition findAcquisitionByOwner(User owner);

    @Transactional
    void removeAcquisitionByOwner(User owner);
}
