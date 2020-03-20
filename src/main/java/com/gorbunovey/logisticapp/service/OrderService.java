package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    void addOrder(OrderDTO orderDTO);
    OrderDTO getOrderByNumber(Long number);
    List<OrderDTO> getOrderList();
    //boolean closeOrder();



}
