//package com.gorbunovey.logisticapp.dao;
//
//import com.gorbunovey.logisticapp.model.Driver;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class DriverDaoDummyImpl implements DriverDao {
//
//    List<Driver> drivers = new ArrayList<>();
//
//    public DriverDaoDummyImpl(){
//        drivers.add(new Driver(1, "Alexandr", "Kolobkov", "Ivanovich", "free", 10));
//        drivers.add(new Driver(2, "Sergey", "Andreykov", "Ivanovich", "busy", 20));
//        drivers.add(new Driver(3, "Evgeniy", "Smirnov", "Ivanovich", "busy", 30));
//        drivers.add(new Driver(4, "Andrey", "Goncharov", "Ivanovich", "free", 40));
//        drivers.add(new Driver(5, "Anton", "Kolodyan", "Ivanovich", "busy", 50));
//    }
//
//    @Override
//    public void addDriver(Driver driver) {
//        drivers.add(driver);
//
//    }
//
//    @Override
//    public Driver getDriver(int id) {
//        return drivers.get(id);
//    }
//
//    @Override
//    public void updateDriver(Driver driver) {
//        drivers.set(driver.getId(),driver);
//    }
//
//    @Override
//    public void deleteDriver(int id) {
//        drivers.remove(id);
//    }
//
//    @Override
//    public List<Driver> getDriverList() {
//        return drivers;
//    }
//}
