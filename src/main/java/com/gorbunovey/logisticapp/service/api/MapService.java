package com.gorbunovey.logisticapp.service.api;

import com.gorbunovey.logisticapp.dto.CityDTO;

import java.util.List;

public interface MapService {
    List<CityDTO> getCityList();
    CityDTO getCityByCode(Long code);
    int getDistanceBetween(Long CityFromCode, Long CityToCode);
}
