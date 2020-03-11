package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.DriverEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TruckDAOImpl implements  TruckDAO {
    @Override
    public void addTruck(DriverEntity driver) {

    }

    @Override
    public DriverEntity getTruck(int id) {
        return null;
    }

    @Override
    public void updateTruck(DriverEntity driver) {

    }

    @Override
    public void deleteTruck(int id) {

    }

    @Override
    public List<DriverEntity> getTruckList() {
        return null;
    }
}
