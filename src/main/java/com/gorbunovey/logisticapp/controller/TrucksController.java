package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.model.Truck;
import com.gorbunovey.logisticapp.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/trucks")
public class TrucksController {

    @Autowired
    @Qualifier("truckServiceImpl")
    TruckService truckService;

    @RequestMapping(method = RequestMethod.GET)
    public String getDriverList(Model model){
        model.addAttribute("trucks", truckService.getTruckList());
        return "trucks/trucks";
    }

//    @RequestMapping(value = "/{regNumber}", method = RequestMethod.GET)
//    public String getTruck...

//     @RequestMapping(value = "/{regNumber}", method = RequestMethod.POST)
//     public String setTruck...

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTruck(
            @RequestParam(value="statusMessage", required=false) String statusMessage,
            Model model){
        model.addAttribute("truck", new Truck());
        model.addAttribute("statusMessage", statusMessage);
        return "trucks/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createTruck(
            @ModelAttribute @Valid Truck truck,
            BindingResult bindingResult,
            Model model){
        // Here should be validation form fields
        // Validate @Valid thus???
        // if success -> return redirect to default new page with success message
        // else -> return to the same page with validation errors message

        if(!bindingResult.hasErrors()){
            truckService.addTruck(truck);
            model.addAttribute("statusMessage", "Success. " + truck.toString());
        }
        return "drivers/new";
    }

//    @RequestMapping(value = "/delete/{regNumber}", method = RequestMethod.GET)
//    public String deleteTruckConfirm...

//    @RequestMapping(value = "/delete/{regNumber}", method = RequestMethod.POST)
//    public String deleteTruck...

}
