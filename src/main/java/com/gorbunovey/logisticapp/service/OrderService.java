package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.dto.OrderDTO;
import com.gorbunovey.logisticapp.dto.TruckDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;

import java.util.List;

public interface OrderService {

    void addOrder(OrderDTO orderDTO);
    OrderDTO getOrderByNumber(Long number);
    List<OrderDTO> getOrderList();
    List<OrderDTO> getActiveOrdersList();
    void addOrder(List<WayPointDTO> wayPoints, TruckDTO truck, List<DriverDTO> drivers);
    //boolean closeOrder();

}
