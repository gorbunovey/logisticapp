package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.CargoDAO;
import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.entity.CargoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("--------------------------" + cargoWithStatusDTOList.isEmpty());
        cargoWithStatusDTOList.forEach(System.out::println);
        return cargoWithStatusDTOList;
    }
}
