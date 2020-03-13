package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.DriverDTO;
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
    @Qualifier("driverServiceImpl")
    DriverService driverService;

    // ---------------------------------------- ALL ----------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public String getDriverList(
            @RequestParam(value = "statusMessage", required = false) String statusMessage,
            Model model) {
        model.addAttribute("drivers", driverService.getDriverList());
        model.addAttribute("statusMessage", statusMessage);
        return "drivers/drivers";
    }

    // ---------------------------------------- EDIT ----------------------------------------

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String getDriver(
            @PathVariable(value = "id") int id,
            Model model) {
        // TODO: Sanity check for id before using service
        model.addAttribute("driver", driverService.getDriver(id));
        return "drivers/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String setDriver(
            @ModelAttribute @Valid DriverDTO driver,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMessage", "Failure. Driver #" + driver.getId() + " wasn't edit");
        }else{
            driverService.updateDriver(driver);
            model.addAttribute("driver", driver);
            model.addAttribute("statusMessage", "Success. Driver #" + driver.getId() + " was edit");
        }
        return "drivers/edit";
    }

    // ---------------------------------------- NEW ----------------------------------------

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDriver(Model model) {
        model.addAttribute("driver", new DriverDTO());
        return "drivers/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createDriver(
            @ModelAttribute("driver") @Valid DriverDTO driver,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMessage", "Failure. Couldn't add new driver");
        } else {
            driverService.addDriver(driver);
            model.addAttribute("statusMessage", "Success. Driver #" + driver.getId() + " was added");
            model.addAttribute("driver", new DriverDTO());
        }
        return "drivers/new";

    }

    // ---------------------------------------- DELETE ----------------------------------------

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteDriver(
            @PathVariable(value = "id") int id,
            Model model) {
        // TODO: Sanity check for id before using service
        model.addAttribute("driver", driverService.getDriver(id));
        return "drivers/delete";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteDriver(
            @PathVariable(value = "id") int id,
            @RequestParam(value = "message", required = false) String statusMessage,
            Model model) {
        // TODO: Sanity check for id before using service
        driverService.deleteDriver(id);
        model.addAttribute("statusMessage", "Success. Driver #" + id + " was deleted");
        return "redirect:drivers";
    }
}
