package com.gorbunovey.logisticapp.dao.api;


import com.gorbunovey.logisticapp.entity.OrderEntity;

import java.util.List;

public interface OrderDAO {

    Long add(OrderEntity entity);
    OrderEntity get(Long id);
    void update(OrderEntity entity);
    void delete(OrderEntity entity);
    List<OrderEntity> getAll();
    List<OrderEntity> getAllActive();
    OrderEntity getActiveByDriverNumber(Long driverNumber);
}
