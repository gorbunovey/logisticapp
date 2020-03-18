package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.CityEntity;

import java.util.List;

public interface CityDAO {
    List<CityEntity> getAll();
    CityEntity getByCode(Long code);
}
