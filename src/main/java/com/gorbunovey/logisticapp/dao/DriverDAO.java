package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.DriverEntity;

import java.util.List;

public interface DriverDAO {

    void add(DriverEntity entity);
    DriverEntity get(Long id);
    void update(DriverEntity entity);
    void delete(DriverEntity entity);
    List<DriverEntity> getAll();

    DriverEntity getByNumber(Long number);
}
