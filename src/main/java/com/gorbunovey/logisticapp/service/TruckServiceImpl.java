package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.model.Truck;
import com.gorbunovey.logisticapp.repository.DriverRepository;
import com.gorbunovey.logisticapp.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TruckServiceImpl implements TruckService {

    @Autowired
    private TruckRepository truckRepository;

    @Override
    @Transactional
    public void addTruck(Truck truck) {
        truckRepository.save(truck);
    }

    @Override
    public Truck getTruck(String regNumber) {
        return truckRepository.findById(regNumber).orElse(null);
    }

    @Override
    @Transactional
    public void updateTruck(Truck truck) {
        truckRepository.save(truck);
    }

    @Override
    @Transactional
    public void deleteTruck(String regNumber) {
        truckRepository.deleteById(regNumber);
    }

    @Override
    public List<Truck> getTruckList() {
        return truckRepository.findAll();
    }
}
