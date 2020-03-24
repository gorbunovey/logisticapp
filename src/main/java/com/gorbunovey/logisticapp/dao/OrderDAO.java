package com.gorbunovey.logisticapp.dao;


import com.gorbunovey.logisticapp.entity.OrderEntity;

import java.util.List;

public interface OrderDAO {

    Long add(OrderEntity entity);
    OrderEntity get(Long id);
    void update(OrderEntity entity);
    void delete(OrderEntity entity);
    List<OrderEntity> getAll();

    OrderEntity getByNumber(Long number);
    List<OrderEntity> getAllActive();
}
