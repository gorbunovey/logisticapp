package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.service.DriverService;
import com.gorbunovey.logisticapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    DriverService driverService;
    @Autowired
    OrderService orderService;

    // Show driver details
    @GetMapping(value = "/{pathNumber}/details")
    public String getDriver(@PathVariable(value = "pathNumber") Long pathNumber, Model model) {
        model.addAttribute("driver", driverService.getDriverByNumber(pathNumber));
        model.addAttribute("order", orderService.getActiveOrderByDriverNumber(pathNumber));
        model.addAttribute("statuses", DriverDTO.statuses);
        return "driver/details";
    }

    // Change driver status
    @PostMapping(value = "/{pathNumber}/status/{pathStatus}")
    public String setDriverStatus(@PathVariable(value = "pathStatus") String pathStatus,
                            @PathVariable(value = "pathNumber") Long pathNumber) {
        driverService.setStatus(pathNumber, pathStatus);
        return "redirect:/driver/{pathNumber}/details";
    }

    // Change driver's shift state
    @PostMapping(value = "/{pathNumber}/shift/{pathShift}")
    public String setDriverShift(@PathVariable(value = "pathShift") boolean pathShift,
                                 @PathVariable(value = "pathNumber") Long pathNumber) {
        driverService.setDriverShift(pathNumber, pathShift);
        return "redirect:/driver/{pathNumber}/details";
    }

    // Change cargo status
    @PostMapping(value = "/{pathNumber}/cargo/{pathCargoNumber}/{pathStatus}")
    public String setCargoStatus(@PathVariable(value = "pathCargoNumber") Long pathCargoNumber,
                                 @PathVariable(value = "pathNumber") Long pathNumber,
                                 @PathVariable(value = "pathStatus") String pathStatus) {
        orderService.updateOrderInfoFromDriver(pathNumber, pathCargoNumber, pathStatus);
        return "redirect:/driver/{pathNumber}/details";
    }
}
