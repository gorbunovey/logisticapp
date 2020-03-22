package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.TruckEntity;

import java.util.List;

public interface TruckDAO {

    void add(TruckEntity entity);
    TruckEntity get(Long id);
    void update(TruckEntity entity);
    void delete(TruckEntity entity);
    List<TruckEntity> getAll();

    TruckEntity getByRegNumber(String regNumber);
    List<TruckEntity> getAllActiveWithCapacity(float capacity);
    List<TruckEntity> getAllActiveWithCapacityAndFree(float capacity);
}
