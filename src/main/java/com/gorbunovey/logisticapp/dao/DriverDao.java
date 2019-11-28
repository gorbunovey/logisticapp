package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.model.Driver;

import java.util.List;

public interface DriverDao {

    public void addDriver(Driver driver);
    public Driver getDriver(int id);
    public void updateDriver(Driver driver);
    public void deleteDriver(int id);
    public List<Driver> getDriverList();
}
