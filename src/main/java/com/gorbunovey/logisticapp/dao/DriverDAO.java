package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.DriverEntity;

import java.util.List;

public interface DriverDAO {

    public void addDriver(DriverEntity driver);
    public DriverEntity getDriver(int id);
    public void updateDriver(DriverEntity driver);
    public void deleteDriver(int id);
    public List<DriverEntity> getDriverList();
}
