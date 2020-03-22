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

    }

    @Override
    public OrderDTO getOrderByNumber(Long number) {
        OrderEntity orderEntity = orderDAO.getByNumber(number);
        if (orderEntity == null){
            return null;
        }else{
            return modelMapper.map(orderEntity, OrderDTO.class);// --------------------------------- неправильно!!!
        }
    }

    @Override
    public List<OrderDTO> getOrderList() {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        System.out.println("---------------getOrderList()------BEFORE DAO----------");
        List<OrderEntity> orderEntityList = orderDAO.getAll();
        System.out.println("---------------getOrderList()------AFTER DAO----BEFORE MAPPING------");
        // Mapping:
        for(OrderEntity entity: orderEntityList){
            // simple fields
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setNumber(entity.getNumber());
            orderDTO.setActive(entity.isActive());
            // drivers
            System.out.println("---------------drivers mapping start----------");
            List<Long> driversNumber = entity.getDrivers().stream()
                    .map(DriverEntity::getNumber)
                    .collect(Collectors.toList());
            orderDTO.setDriversNumber(driversNumber);
            driversNumber.forEach(System.out::println);
            System.out.println("---------------drivers mapping end----------");
            // waypoints
            System.out.println("---------------waypoints mapping start----------");
            List<WayPointDTO> wayPointDTOList = new ArrayList<>();



            List<WayPointEntity> temp = entity.getWayPoints().stream().collect(Collectors.toList());
            System.out.println("---------------AFTER --entity.getWayPoints()--------");
            temp.forEach(System.out::println);




            temp.forEach(wayPointEntity -> {
                        WayPointDTO wayPointDTO = new WayPointDTO();
                        wayPointDTO.setSeqNumber(wayPointEntity.getSeqNumber());
                        wayPointDTO.setType(wayPointEntity.isType());
                        wayPointDTO.setOrderNumber(wayPointEntity.getOrder().getNumber());
                        wayPointDTO.setCargo(modelMapper.map(wayPointEntity.getCargo(), CargoDTO.class));
                        wayPointDTOList.add(wayPointDTO);
                    });
            orderDTO.setWayPoints(wayPointDTOList);
            wayPointDTOList.forEach(System.out::println);
            System.out.println("---------------waypoints mapping end----------");
            // and add to list
            orderDTOList.add(orderDTO);
        }
        System.out.println("---------------getOrderList()------AFTER MAPPING----------");
        System.out.println("---------------orderDTOList.isEmpty()----------------" + orderDTOList.isEmpty());
        orderDTOList.forEach(System.out::println);
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getActiveOrdersList() {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderDAO.getAllActive().forEach(orderEntity -> orderDTOList.add(modelMapper.map(orderEntity, OrderDTO.class)));
        return orderDTOList;
    }
}
