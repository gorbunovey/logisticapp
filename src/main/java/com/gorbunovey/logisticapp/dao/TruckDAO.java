package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.DriverEntity;

import java.util.List;

public interface TruckDAO {

    public void addTruck(DriverEntity driver);
    public DriverEntity getTruck(int id);
    public void updateTruck(DriverEntity driver);
    public void deleteTruck(int id);
    public List<DriverEntity> getTruckList();
}
