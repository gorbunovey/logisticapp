package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/drivers")
public class DriversController {
    @Autowired
    DriverService driverService;

    @RequestMapping(method = RequestMethod.GET)
    public String getDriverList(Model model){
        model.addAttribute("drivers", driverService.getDriverList());
        return "drivers/drivers";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getDriver(
            @PathVariable(value = "id") int id,
            @RequestParam(value="message", required=false) String message,
            Model model){
        // Here should be validating id
        // if no such driver -> then no driver attribute
        model.addAttribute("message", message);
        model.addAttribute("driver", driverService.getDriver(id));
        return "drivers/driver";
    }

    // @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    // public String setDriver...
    // Here POST-method save changes of driver

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDriver(
            @RequestParam(value="message", required=false) String message,
            Model model){
        //model.addAttribute("driver", new Driver())
        model.addAttribute("message", message);
        return "drivers/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createDriver(Model model){
        // Here should be validation form fields
        // Catch a person by @ModelAttribute("Driver") Driver driver
        // Validate @Valid thus???
        // Here try to driverService.createDriver(new Driver from form)
        // if success -> return redirect to default new page with success message
        // else -> return to the same page with validation errors message
        return "drivers/new";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteDriverConfirm(
            @PathVariable(value = "id") int id,
            @RequestParam(value="message", required=false) String message,
            Model model){
        // Here should be validating id
        // if no such driver -> then no driver attribute
        model.addAttribute("message", message);
        model.addAttribute("driver", driverService.getDriver(id));
        // return the confirmation form for deleting
        return "drivers/delete";
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteDriver(
            @PathVariable(value = "id") int id,
            Model model){
        // Here should be validating id
        // if no such driver -> then error msg
        driverService.deleteDriver(id);
        return "drivers/delete";
    }
}
