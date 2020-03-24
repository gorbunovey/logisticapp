package com.gorbunovey.logisticapp.dto.converters;

import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.dto.OrderDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import com.gorbunovey.logisticapp.entity.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    private ModelMapper modelMapper = new ModelMapper();

    public OrderDTO mapEntityToDto(OrderEntity entity) {
        OrderDTO orderDTO = new OrderDTO();
        // simple fields
        orderDTO.setNumber(entity.getId());
        orderDTO.setActive(entity.isActive());
        orderDTO.setTruckRegNumber(entity.getTruck().getRegNumber());
        // drivers
        List<Long> driversNumber = entity.getDrivers().stream()
                .map(DriverEntity::getNumber)
                .collect(Collectors.toList());
        orderDTO.setDriversNumber(driversNumber);
        driversNumber.forEach(System.out::println);
        // wayPoints
        List<WayPointDTO> wayPointDTOList = new ArrayList<>();
        entity.getWayPoints().forEach(wayPointEntity -> {
            WayPointDTO wayPointDTO = new WayPointDTO();
            wayPointDTO.setSeqNumber(wayPointEntity.getSeqNumber());
            wayPointDTO.setType(wayPointEntity.isType());
            wayPointDTO.setOrderNumber(entity.getId());
            wayPointDTO.setCargo(modelMapper.map(wayPointEntity.getCargo(), CargoDTO.class));
            wayPointDTOList.add(wayPointDTO);
        });
        orderDTO.setWayPoints(wayPointDTOList);
        return orderDTO;
    }

    public List<OrderDTO> mapEntityListToDto(List<OrderEntity> orderEntityList) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity entity : orderEntityList) {
            orderDTOList.add(mapEntityToDto(entity));
        }
        return orderDTOList;
    }
}
