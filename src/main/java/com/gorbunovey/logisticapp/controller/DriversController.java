package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.model.Driver;
import com.gorbunovey.logisticapp.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            @RequestParam(value="statusMessage", required=false) String statusMessage,
            Model model){
        // Here should be validating id
        // if no such driver -> then no driver attribute
        model.addAttribute("statusMessage", statusMessage);
        model.addAttribute("driver", driverService.getDriver(id));
        return "drivers/driver";
    }

    // @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    // public String setDriver...
    // Here POST-method save changes of driver

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDriver(
            @RequestParam(value="statusMessage", required=false) String statusMessage,
            Model model){
        model.addAttribute("driver", new Driver());
        model.addAttribute("statusMessage", statusMessage);
        return "drivers/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createDriver(
            @ModelAttribute @Valid Driver driver,
           BindingResult bindingResult,
           Model model){
        // Here should be validation form fields
        // Validate @Valid thus???
        // if success -> return redirect to default new page with success message
        // else -> return to the same page with validation errors message

        if(!bindingResult.hasErrors()){
            driverService.addDriver(driver);
            model.addAttribute("statusMessage", "Success. " + driver.toString());
        }
        return "drivers/new";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteDriverConfirm(
            @PathVariable(value = "id") int id,
            @RequestParam(value="message", required=false) String statusMessage,
            Model model){
        // Here should be validating id
        // if no such driver -> then no driver attribute
        model.addAttribute("statusMessage", statusMessage);
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
