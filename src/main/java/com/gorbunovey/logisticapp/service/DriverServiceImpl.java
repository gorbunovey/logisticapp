package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.DriverDao;
import com.gorbunovey.logisticapp.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DriverServiceImpl implements DriverService {

    @Autowired
    //Qualifier("driverDao")
    private DriverDao driverDao;

    @Override
    public void createDriver(Driver driver) {
        this.driverDao.createDriver(driver);
    }

    @Override
    public Driver getDriver(int id) {
        return driverDao.readDriver(id);
    }

    @Override
    public void updateDriver(Driver driver) {
        this.driverDao.updateDriver(driver);
    }

    @Override
    public void deleteDriver(int id) {
        this.driverDao.deleteDriver(id);
    }

    @Override
    public List<Driver> getDriverList() {
        return driverDao.getDriverList();
    }
}
