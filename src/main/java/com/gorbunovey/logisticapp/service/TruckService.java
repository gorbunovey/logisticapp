package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.TruckDTO;

import java.util.List;

public interface TruckService {
    void addTruck(TruckDTO truckDTO);
    TruckDTO getTruckByRegNumber(String regNumber);
    boolean updateTruck(TruckDTO truckDTO);
    boolean deleteTruck(String regNumber);
    List<TruckDTO> getTruckList();
    List<TruckDTO> getAllActiveWithCapacity(float capacity);
    List<TruckDTO> getAllActiveWithCapacityAndFree(float capacity);
}
