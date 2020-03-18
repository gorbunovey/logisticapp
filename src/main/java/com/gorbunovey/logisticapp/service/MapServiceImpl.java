package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.CityDAO;
import com.gorbunovey.logisticapp.dto.CityDTO;
import com.gorbunovey.logisticapp.entity.CityEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MapServiceImpl implements MapService {
    @Autowired
    private CityDAO cityDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<CityDTO> getCityList() {
        List<CityDTO> cityDTOList = new ArrayList<>();
        cityDAO.getAll().forEach(cityEntity -> cityDTOList.add(modelMapper.map(cityEntity, CityDTO.class)));
        return cityDTOList;
    }

    @Override
    public CityDTO getCityByCode(Long code) {
        CityEntity cityEntity = cityDAO.getByCode(code);
        if (cityEntity == null){
            return null;
        }else {
            return modelMapper.map(cityEntity, CityDTO.class);
        }
    }
}