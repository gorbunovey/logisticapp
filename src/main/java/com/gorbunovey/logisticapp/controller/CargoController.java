package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.service.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cargo")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    // ---------------------------------------- ALL ----------------------------------------

    @GetMapping
    public String getCargoList(Model model) {
        model.addAttribute("cargoList", cargoService.getCargoList());
        return "cargo/cargo";
    }

}
