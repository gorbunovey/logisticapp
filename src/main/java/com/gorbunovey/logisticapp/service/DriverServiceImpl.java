package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.DriverDAO;
import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDAO driverDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public void addDriver(DriverDTO driver) {
        DriverEntity driverEntity = modelMapper.map(driver, DriverEntity.class);
        driverDAO.add(driverEntity);

    }

    @Override
    public DriverDTO getDriver(int id) {
        DriverEntity driverEntity = driverDAO.get(id);
        if (driverEntity == null){
            return null;
        }else {
            return modelMapper.map(driverEntity, DriverDTO.class);
        }

    }

    @Override
    @Transactional
    public void updateDriver(DriverDTO driverDTO) {
        DriverEntity driverEntity = driverDAO.get(driverDTO.getId());
        modelMapper.map(driverDTO, driverEntity);
        driverDAO.update(driverEntity);
    }

    @Override
    @Transactional
    public void deleteDriver(int id) {
        driverDAO.delete(driverDAO.get(id));
    }

    @Override
    public List<DriverDTO> getDriverList() {
        List<DriverDTO> driverDTOList = new ArrayList<>();
        driverDAO.getAll().forEach( driverEntity -> driverDTOList.add(modelMapper.map(driverEntity, DriverDTO.class)));
        return driverDTOList;
    }

}
