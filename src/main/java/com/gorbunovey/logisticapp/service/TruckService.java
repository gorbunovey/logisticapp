package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.model.Truck;

import java.util.List;

public interface TruckService {
    public void addTruck(Truck truck);
    public Truck getTruck(String regNumber);
    public void updateTruck(Truck truck);
    public void deleteTruck(String regNumber);
    public List<Truck> getTruckList();
}
