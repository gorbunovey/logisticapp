package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.model.Driver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DriverDaoDummyImpl implements DriverDao {

    List<Driver> drivers = Arrays.asList(
            new Driver(1, "Alexandr", "Kolobkov", 10, "free"),
            new Driver(2, "Sergey", "Andreykov", 20, "busy"),
            new Driver(3, "Evgeniy", "Smirnov", 30, "busy"),
            new Driver(4, "Andrey", "Goncharov", 40, "free"),
            new Driver(5, "Anton", "Kolodyan", 50, "busy")
    );

    @Override
    public void createDriver(Driver driver) {
        drivers.add(driver);

    }

    @Override
    public Driver readDriver(int id) {
        return drivers.get(id);
    }

    @Override
    public void updateDriver(Driver driver) {
        drivers.set(driver.getId(),driver);
    }

    @Override
    public void deleteDriver(Driver driver) {
        drivers.remove(driver.getId());
    }

    @Override
    public List<Driver> getDriverList() {
        return drivers;
    }
}
