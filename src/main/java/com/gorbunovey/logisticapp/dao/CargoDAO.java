package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.CargoEntity;

import java.util.List;

public interface CargoDAO {

    void add(CargoEntity entity);
    CargoEntity get(Long id);
    void update(CargoEntity entity);
    void delete(CargoEntity entity);
    List<CargoEntity> getAll();
}
