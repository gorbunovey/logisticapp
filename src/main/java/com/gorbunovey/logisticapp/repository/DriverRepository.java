package com.gorbunovey.logisticapp.repository;

import com.gorbunovey.logisticapp.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    //Driver findByFirstName(String firstName);
}
