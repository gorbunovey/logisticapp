package com.gorbunovey.logisticapp.service.impl;

import com.gorbunovey.logisticapp.dao.api.CargoDAO;
import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.entity.CargoEntity;
import com.gorbunovey.logisticapp.service.api.CargoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CargoServiceImpl implements CargoService {

    @Autowired
    CargoDAO cargoDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CargoDTO getCargoByNumber(Long number) {
        CargoEntity cargoEntity = cargoDAO.getByNumber(number);
        if (cargoEntity == null) {
            return null;
        } else {
            return modelMapper.map(cargoEntity, CargoDTO.class);
        }
    }

    @Override
    public List<CargoDTO> getCargoList() {
        List<CargoDTO> cargoDTOList = new ArrayList<>();
        cargoDAO.getAll().forEach(cargoEntity -> cargoDTOList.add(modelMapper.map(cargoEntity, CargoDTO.class)));
        return cargoDTOList;
    }

    @Override
    public List<CargoDTO> getCargoWithStatus(String status) {
        List<CargoDTO> cargoWithStatusDTOList = new ArrayList<>();
        cargoDAO.findWithStatus(status).forEach(cargoEntity -> cargoWithStatusDTOList.add(modelMapper.map(cargoEntity, CargoDTO.class)));
        return cargoWithStatusDTOList;
    }

    @Override
    public List<CargoDTO> getCargoWithStatusFromCity(Long cityCode, String status) {
        List<CargoDTO> withStatusCargo = this.getCargoWithStatus(status);
        return withStatusCargo.stream()
                .filter(c->c.getCityFromCode().equals(cityCode))
                .collect(Collectors.toList());
    }

    @Override
    public List<CargoDTO> getCargoWithStatusWithoutOrder(String status) {
        List<CargoDTO> cargoDTOList = new ArrayList<>();
        cargoDAO.findWithStatusWithoutOrder(status)
                .forEach(cargoEntity -> cargoDTOList.add(modelMapper.map(cargoEntity, CargoDTO.class)));
        return cargoDTOList;
    }

    @Override
    public List<CargoDTO> getCargoWithStatusFromCityWithoutOrder(Long cityCode, String status) {
        List<CargoDTO> cargoDTOList = new ArrayList<>();
        cargoDAO.findWithStatusInCityWithoutOrder(status, cityCode)
                .forEach(cargoEntity -> cargoDTOList.add(modelMapper.map(cargoEntity, CargoDTO.class)));
        return cargoDTOList;
    }

    @Override
    @Transactional
    public void setCargoStatus(Long number, String status) {
        CargoEntity cargoEntity = cargoDAO.getByNumber(number);
        cargoEntity.setStatus(status);
    }
}
