package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.DriverEntity;

import java.util.List;

public interface DriverDAO {

    public void add(DriverEntity entity);
    public DriverEntity get(int id);
    public void update(DriverEntity entity);
    public void delete(DriverEntity entity);
    public List<DriverEntity> getAll();
}
