package com.gorbunovey.logisticapp.repository;

import com.gorbunovey.logisticapp.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, String> {
}
