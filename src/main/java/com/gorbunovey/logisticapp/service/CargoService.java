package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.CargoDTO;

import java.util.List;

public interface CargoService {

    CargoDTO getCargoByNumber(Long number);
    List<CargoDTO> getCargoList();
    List<CargoDTO> getCargoWithStatus(String status);
}
