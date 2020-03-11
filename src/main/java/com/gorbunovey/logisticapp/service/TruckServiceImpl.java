package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.TruckDAO;
import com.gorbunovey.logisticapp.dto.TruckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TruckServiceImpl implements TruckService {

    @Autowired
    private TruckDAO truckDAO;

    @Override
    @Transactional
    public void addTruck(TruckDTO truck) {
       //truckRepository.save(truck);
    }

    @Override
    public TruckDTO getTruck(String regNumber) {
        //return truckRepository.findById(regNumber).orElse(null);
        return null;
    }

    @Override
    @Transactional
    public void updateTruck(TruckDTO truck) {
        //truckRepository.save(truck);
    }

    @Override
    @Transactional
    public void deleteTruck(String regNumber) {
        //truckRepository.deleteById(regNumber);
    }

    @Override
    public List<TruckDTO> getTruckList() {
        //return truckRepository.findAll();
        return null;
    }
}
