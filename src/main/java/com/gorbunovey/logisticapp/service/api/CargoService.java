package com.gorbunovey.logisticapp.service.api;

import com.gorbunovey.logisticapp.dto.CargoDTO;

import java.util.List;

public interface CargoService {

    CargoDTO getCargoByNumber(Long number);
    List<CargoDTO> getCargoList();
    List<CargoDTO> getCargoWithStatus(String status);
    List<CargoDTO> getCargoWithStatusFromCity(Long cityCode, String status);
    List<CargoDTO> getCargoWithStatusWithoutOrder(String status);
    List<CargoDTO> getCargoWithStatusFromCityWithoutOrder(Long cityCode, String status);
    void setCargoStatus(Long number, String status);
}
