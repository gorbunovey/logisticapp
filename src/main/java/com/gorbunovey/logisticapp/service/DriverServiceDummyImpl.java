package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.DriverDao;
import com.gorbunovey.logisticapp.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceDummyImpl implements DriverService {

    @Autowired
    DriverDao driverDao;

    @Override
    public void addDriver(Driver driver) {
        driverDao.addDriver(driver);

    }

    @Override
    public Driver getDriver(int id) {
        return driverDao.getDriver(id);
    }

    @Override
    public void updateDriver(Driver driver) {

    }

    @Override
    public void deleteDriver(int id) {

    }

    @Override
    public List<Driver> getDriverList() {
        return driverDao.getDriverList();
    }
}
