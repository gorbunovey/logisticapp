package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.model.Driver;

import java.util.List;

public interface DriverDao {

    public void createDriver(Driver driver);
    public Driver readDriver(int id);
    public void updateDriver(Driver driver);
    public void deleteDriver(Driver driver);
    public List<Driver> getDriverList();
}
