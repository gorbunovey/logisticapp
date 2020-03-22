package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.OrderDAO;
import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.dto.OrderDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import com.gorbunovey.logisticapp.entity.OrderEntity;
import com.gorbunovey.logisticapp.entity.WayPointEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void addOrder(OrderDTO orderDTO) {
        // TODO: сделать метод + маппер из ДТО в сущность
    }

    @Override
    public OrderDTO getOrderByNumber(Long number) {
        OrderEntity orderEntity = orderDAO.getByNumber(number);
        if (orderEntity == null){
            return null;
        }else{
            return mapEntityToDto(orderEntity);
        }
    }

    @Override
    public List<OrderDTO> getOrderList() {
        List<OrderEntity> orderEntityList = orderDAO.getAll();
        List<OrderDTO>  orderDTOList = mapEntityListToDto(orderEntityList);
        orderDTOList.forEach(System.out::println);
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getActiveOrdersList() {
        return mapEntityListToDto(orderDAO.getAllActive());
    }

    // Mappers:

    private OrderDTO mapEntityToDto(OrderEntity entity){
        OrderDTO orderDTO = new OrderDTO();
        // simple fields
        orderDTO.setNumber(entity.getNumber());
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
            wayPointDTO.setOrderNumber(entity.getNumber());
            wayPointDTO.setCargo(modelMapper.map(wayPointEntity.getCargo(), CargoDTO.class));
            wayPointDTOList.add(wayPointDTO);
        });
        orderDTO.setWayPoints(wayPointDTOList);
        return orderDTO;
    }

    private List<OrderDTO> mapEntityListToDto(List<OrderEntity> orderEntityList){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderEntity entity: orderEntityList){
            orderDTOList.add(mapEntityToDto(entity));
        }
        return orderDTOList;
    }
}
