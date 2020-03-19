package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.DriverDTO;

import java.util.List;

public interface DriverService {

    void addDriver(DriverDTO driverDTO);
    DriverDTO getDriverByNumber(Long number);
    boolean updateDriver(DriverDTO driverDTO);
    boolean deleteDriver(Long number);
    List<DriverDTO> getDriverList();

}
