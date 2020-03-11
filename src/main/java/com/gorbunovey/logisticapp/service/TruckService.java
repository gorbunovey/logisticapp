package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.TruckDTO;

import java.util.List;

public interface TruckService {
    public void addTruck(TruckDTO truck);
    public TruckDTO getTruck(String regNumber);
    public void updateTruck(TruckDTO truck);
    public void deleteTruck(String regNumber);
    public List<TruckDTO> getTruckList();
}
