package com.gorbunovey.logisticapp.service.impl;

import com.gorbunovey.logisticapp.dao.api.CityDAO;
import com.gorbunovey.logisticapp.dto.CityDTO;
import com.gorbunovey.logisticapp.entity.CityEntity;
import com.gorbunovey.logisticapp.entity.MapEntity;
import com.gorbunovey.logisticapp.service.api.MapService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return (cityEntity == null ? null: modelMapper.map(cityEntity, CityDTO.class));
    }

    @Override
    public int getDistanceBetween(Long CityFromCode, Long CityToCode) {
        if(CityFromCode.equals(CityToCode)){
            return 0;
        }
        CityEntity cityEntity = cityDAO.getByCode(CityFromCode);
        Optional<MapEntity> edgeOptional = cityEntity.getDeparturePoints().stream()
                .filter(p->p.getDestinationPoint().getCode() == CityToCode)
                .findAny();
        return edgeOptional.map(MapEntity::getDistance).orElse(-Integer.MAX_VALUE);
    }
}