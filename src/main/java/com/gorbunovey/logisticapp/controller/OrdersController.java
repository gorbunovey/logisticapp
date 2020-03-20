package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    // ---------------------------------------- ALL ----------------------------------------

    @GetMapping
    public String getOrderList(Model model) {
        model.addAttribute("orderList", orderService.getOrderList());
        return "orders/orders";
    }
}
