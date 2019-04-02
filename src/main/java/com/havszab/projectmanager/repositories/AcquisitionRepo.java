package com.havszab.projectmanager.repositories;

import com.havszab.projectmanager.model.Acquisition;
import com.havszab.projectmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquisitionRepo extends JpaRepository<Acquisition, Long> {

    Acquisition findAcquisitionByOwner(User owner);
}
