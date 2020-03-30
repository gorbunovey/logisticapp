package com.gorbunovey.logisticapp.service.impl;

import com.gorbunovey.logisticapp.dao.api.CityDAO;
import com.gorbunovey.logisticapp.dao.api.TruckDAO;
import com.gorbunovey.logisticapp.dto.TruckDTO;
import com.gorbunovey.logisticapp.entity.CityEntity;
import com.gorbunovey.logisticapp.entity.TruckEntity;
import com.gorbunovey.logisticapp.service.api.TruckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class TruckServiceImpl implements TruckService {

    @Autowired
    private TruckDAO truckDAO;
    @Autowired
    private CityDAO cityDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public void addTruck(TruckDTO truckDTO) {
        TruckEntity truckEntity = modelMapper.map(truckDTO, TruckEntity.class);
        // Must take city entity from DB, cause modelMapper made a new and it cannot be used
        CityEntity cityEntity = cityDAO.getByCode(truckDTO.getCityCode());
        truckEntity.setCity(cityEntity);
        truckDAO.add(truckEntity);
    }

    @Override
    public TruckDTO getTruckByRegNumber(String regNumber) {
        TruckEntity truckEntity = truckDAO.getByRegNumber(regNumber);
        return (truckEntity == null ? null: modelMapper.map(truckEntity, TruckDTO.class));
    }

    @Override
    @Transactional
    public boolean updateTruck(TruckDTO truckDTO) {
        TruckEntity truckEntity = truckDAO.getByRegNumber(truckDTO.getOldRegNumber());
        if (truckEntity == null) {
            return false;
        } else {
            // can't use model mapper here because it's try to create inner objects, but can't do it properly
            // for example - in this case it tries create a new city which will conflict with one in DB
            truckEntity.setRegNumber(truckDTO.getRegNumber());
            truckEntity.setCrew(truckDTO.getCrew());
            truckEntity.setCapacity(truckDTO.getCapacity());
            truckEntity.setActive(truckDTO.isActive());
            truckEntity.setCity(cityDAO.getByCode(truckDTO.getCityCode()));
            truckDAO.update(truckEntity);
            return true;
        }

    }

    @Override
    @Transactional
    public boolean deleteTruck(String regNumber) {
        TruckEntity truckEntity = truckDAO.getByRegNumber(regNumber);
        if (truckEntity == null) {
            return false;
        } else {
            truckDAO.delete(truckEntity);
            return true;
        }
    }

    @Override
    public List<TruckDTO> getTruckList() {
        List<TruckDTO> truckDTOList = new ArrayList<>();
        truckDAO.getAll().forEach(truckEntity -> truckDTOList.add(modelMapper.map(truckEntity, TruckDTO.class)));
        return truckDTOList;
    }

    @Override
    public List<TruckDTO> getAllActiveWithCapacity(float capacity) {
        List<TruckDTO> truckDTOList = new ArrayList<>();
        truckDAO.getAllActiveWithCapacity(capacity).forEach(truckEntity -> truckDTOList.add(modelMapper.map(truckEntity, TruckDTO.class)));
        return truckDTOList;
    }

    @Override
    public List<TruckDTO> getAllActiveWithCapacityAndFree(float capacity) {
        List<TruckDTO> truckDTOList = new ArrayList<>();
        truckDAO.getAllActiveWithCapacityAndFree(capacity).forEach(truckEntity -> truckDTOList.add(modelMapper.map(truckEntity, TruckDTO.class)));
        return truckDTOList;
    }
}