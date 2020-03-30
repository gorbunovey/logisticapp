package com.gorbunovey.logisticapp.service.impl;

import com.gorbunovey.logisticapp.dao.api.CargoDAO;
import com.gorbunovey.logisticapp.dao.api.DriverDAO;
import com.gorbunovey.logisticapp.dao.api.OrderDAO;
import com.gorbunovey.logisticapp.dao.api.TruckDAO;
import com.gorbunovey.logisticapp.dto.*;
import com.gorbunovey.logisticapp.converters.OrderConverter;
import com.gorbunovey.logisticapp.entity.CityEntity;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import com.gorbunovey.logisticapp.entity.OrderEntity;
import com.gorbunovey.logisticapp.entity.WayPointEntity;
import com.gorbunovey.logisticapp.service.api.CargoService;
import com.gorbunovey.logisticapp.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    TruckDAO truckDAO;// will go to the converter when refactoring OrderDTO
    @Autowired
    DriverDAO driverDAO;// will go to the converter when refactoring OrderDTO
    @Autowired
    CargoDAO cargoDAO;// will go to the converter when refactoring OrderDTO
    @Autowired
    CargoService cargoService;
    @Autowired
    OrderConverter converter;

    @Override
    @Transactional
    public void addOrder(OrderDTO orderDTO) {
        // TODO: made method when refactoring OrderDTO
    }

    @Override
    public OrderDTO getOrderByNumber(Long number) {
        OrderEntity orderEntity = orderDAO.get(number);
        return (orderEntity == null ? null: converter.mapEntityToDto(orderEntity));
    }

    @Override
    public List<OrderDTO> getOrderList() {
        List<OrderEntity> orderEntityList = orderDAO.getAll();
        List<OrderDTO> orderDTOList = converter.mapEntityListToDto(orderEntityList);
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getActiveOrdersList() {
        return converter.mapEntityListToDto(orderDAO.getAllActive());
    }

    @Override
    @Transactional
    public void addOrder(List<WayPointDTO> wayPoints, TruckDTO truck, List<DriverDTO> drivers) {
        // Handmade mapping - will be exclude after refactor OrderDTO
        OrderEntity newOrderEntity = new OrderEntity();
        newOrderEntity.setActive(true);
        newOrderEntity.setTruck(truckDAO.getByRegNumber(truck.getRegNumber()));
        drivers.forEach(driver -> newOrderEntity.getDrivers().add(driverDAO.getByNumber(driver.getNumber())));
        // wayPoints
        wayPoints.forEach(wayPointDTO -> {
            WayPointEntity wayPointEntity = new WayPointEntity();
            wayPointEntity.setSeqNumber(wayPointDTO.getSeqNumber());
            wayPointEntity.setType(wayPointDTO.isType());
            wayPointEntity.setCargo(cargoDAO.getByNumber(wayPointDTO.getCargo().getNumber()));
            newOrderEntity.addWayPoint(wayPointEntity);
        });
        if (checkNewOrder(newOrderEntity)) {
            orderDAO.add(newOrderEntity);
        }
    }

    @Override
    @Transactional
    public boolean closeOrderIfAllCargoDelivered(Long number) {
        OrderEntity orderEntity = orderDAO.get(number);
        Optional<WayPointEntity> notReachedWayPoint = orderEntity.getWayPoints().stream()
                .filter(wayPoint -> !wayPoint.getCargo().getStatus().equalsIgnoreCase("delivered"))
                .findAny();
        if (notReachedWayPoint.isPresent()) {
            return false;
        } else {
            orderEntity.setActive(false);
            orderDAO.update(orderEntity);
            return true;
        }
    }

    @Override
    public Long getActiveOrderNumberByDriverNumber(Long driverNumber) {
        OrderEntity orderEntity = orderDAO.getActiveByDriverNumber(driverNumber);
        return (orderEntity == null ? null: orderEntity.getId());
    }

    @Override
    public OrderDTO getActiveOrderByDriverNumber(Long driverNumber) {
        OrderEntity orderEntity = orderDAO.getActiveByDriverNumber(driverNumber);
        return (orderEntity == null ? null: converter.mapEntityToDto(orderEntity));
    }

    @Override
    @Transactional
    public void updateOrderInfoFromDriver(Long driverNumber, Long cargoNumber, String newCargoStatus) {
        // update cargo status
        cargoService.setCargoStatus(cargoNumber, newCargoStatus);
        OrderEntity orderEntity = orderDAO.getActiveByDriverNumber(driverNumber);
        // set new city to drivers and truck:
        CityEntity newCity = null;
        // find current way point and extract the city
        for (WayPointEntity point : orderEntity.getWayPoints()) {
            // filter by cargo
            if (point.getCargo().getNumber() == cargoNumber) {
                // filter by process. It's needed because when status "Delivered" will be 2 wayPoints with current cargo
                // if unloading
                if (newCargoStatus.equalsIgnoreCase("delivered")) {
                    newCity = point.getCargo().getCityTo();
                    break;
                }
                // if loading
                if (newCargoStatus.equalsIgnoreCase("loaded")) {
                    newCity = point.getCargo().getCityFrom();
                    break;
                }
            }
        }
        CityEntity finalNewCity = newCity;
        orderEntity.getDrivers().forEach(driver -> driver.setCity(finalNewCity));
        orderEntity.getTruck().setCity(finalNewCity);
        // close order if it was last cargo
        closeOrderIfAllCargoDelivered(orderEntity.getId());
    }

    // redo to service checkNewOrder(OrderDTO newOrderDTO) and add to api, after refactoring OrderDTO
    public boolean checkNewOrder(OrderEntity newOrderEntity) {
        // Truck checks:
        // Truck capacity don't change (add check from shipment later with refactor)
        boolean truckIsGood = true;
        truckIsGood = newOrderEntity.getTruck().isActive();
        for (OrderEntity order : newOrderEntity.getTruck().getOrders()) {
            if (order.isActive()) {
                truckIsGood = false;
                break;
            }
        }
        // Cargo checks:
        // Masses calculated at DTO construction and it hardly can be changed (add check from shipment later with refactor)
        boolean cargoIsGood = true;
        for (WayPointEntity point : newOrderEntity.getWayPoints()) {
            if (!point.getCargo().getStatus().equalsIgnoreCase("Prepared")) {
                cargoIsGood = false;
                break;
            }
        }
        // Drivers checks:
        boolean diversIsGood = true;
        List<DriverEntity> availableDrivers = driverDAO.getAllInCityWithoutOrder(newOrderEntity.getTruck().getCity().getCode());
        if (!availableDrivers.containsAll(newOrderEntity.getDrivers()) ||
                newOrderEntity.getTruck().getCrew() != newOrderEntity.getDrivers().size()) {
            diversIsGood = false;
        }
        return truckIsGood && cargoIsGood && diversIsGood;
    }
}
