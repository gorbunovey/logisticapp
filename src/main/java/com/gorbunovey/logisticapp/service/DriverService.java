package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.DriverDTO;

import java.util.List;

public interface DriverService {

    void addDriver(DriverDTO driver);
    DriverDTO getDriver(Long id);
    void updateDriver(DriverDTO driver);
    void deleteDriver(Long id);
    List<DriverDTO> getDriverList();
}
