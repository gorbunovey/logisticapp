package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.OrderDAO;
import com.gorbunovey.logisticapp.dto.OrderDTO;
import com.gorbunovey.logisticapp.entity.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        orderDAO.getAll().forEach(orderEntity -> orderDTOList.add(modelMapper.map(orderEntity, OrderDTO.class)));
        System.out.println("-------------------------------");
        orderDTOList.forEach(System.out::println);
        return orderDTOList;
    }
}
