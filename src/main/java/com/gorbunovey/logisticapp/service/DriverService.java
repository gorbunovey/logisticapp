package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.model.Driver;

import java.util.List;

public interface DriverService {

    public void createDriver(Driver driver);
    public Driver getDriver(int id);
    public void updateDriver(Driver driver);
    public void deleteDriver(int id);
    public List<Driver> getDriverList();
}