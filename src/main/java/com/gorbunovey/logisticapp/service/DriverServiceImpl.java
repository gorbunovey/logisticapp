package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.model.Driver;
import com.gorbunovey.logisticapp.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    @Transactional
    public void addDriver(Driver driver) {
        driverRepository.save(driver);
    }

    @Override
    public Driver getDriver(int id) {
        //return driverRepository.findById(id).get(); // NoSuchElementException
        //what better in this case?
        return driverRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateDriver(Driver driver) {
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void deleteDriver(int id) {
        driverRepository.deleteById(id);
    }

    @Override
    public List<Driver> getDriverList() {
        return driverRepository.findAll();
    }

    //@Override
    public boolean isExist(int id) {
        return driverRepository.existsById(id);
    }
}
