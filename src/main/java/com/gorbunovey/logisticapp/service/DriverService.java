package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.DriverDTO;

import java.util.List;

public interface DriverService {

    public void addDriver(DriverDTO driver);
    public DriverDTO getDriver(int id);
    public void updateDriver(DriverDTO driver);
    public void deleteDriver(int id);
    public List<DriverDTO> getDriverList();
}
