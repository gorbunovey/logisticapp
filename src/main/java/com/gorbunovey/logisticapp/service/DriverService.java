package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.DriverDTO;

import java.util.List;

public interface DriverService {

    int MAX_HOURS = 176;
    int SHIFT_HOURS = 9;

    void addDriver(DriverDTO driverDTO);
    DriverDTO getDriverByNumber(Long number);
    boolean updateDriver(DriverDTO driverDTO);
    boolean deleteDriver(Long number);
    List<DriverDTO> getDriverList();
    List<DriverDTO> GetAllFreeInCityWithHours(Long cityCode, int driveTime);
    void setDriverShift(Long driverNumber, boolean isOnShift);
    void filterDriverList(List<DriverDTO> sourceList, List<DriverDTO> subtractDrivers);
    void setStatus(Long driverNumber, String newStatus);
}
