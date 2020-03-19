package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.service.DriverService;
import com.gorbunovey.logisticapp.service.MapService;
import com.gorbunovey.logisticapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/drivers")
public class DriversController {

    @Autowired
    @Qualifier("driverServiceImpl")
    DriverService driverService;

    @Autowired
    private MapService mapService;

    @Autowired
    private UserService userService;

    // ---------------------------------------- ALL ----------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public String getDriverList(Model model) {
        model.addAttribute("drivers", driverService.getDriverList());
        return "drivers/drivers";
    }

    // ---------------------------------------- EDIT ----------------------------------------

    @RequestMapping(value = "/edit/{pathNumber}", method = RequestMethod.GET)
    public String getDriver(@PathVariable(value = "pathNumber") Long pathNumber, Model model) {
        // TODO: Sanity check for id before using service
        model.addAttribute("driver", driverService.getDriverByNumber(pathNumber));
        model.addAttribute("cityList", mapService.getCityList());
        model.addAttribute("userList", userService.getUsersWithRole("Guest"));
        return "drivers/edit";
    }

    @RequestMapping(value = "/edit/{pathNumber}", method = RequestMethod.POST)
    public String setDriver(@PathVariable(value = "pathNumber") Long pathNumber,
                            @ModelAttribute("driver") @Valid DriverDTO driverDTO, BindingResult bindingResult,
                            final RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMsg", "Failure. Driver #" + pathNumber + " wasn't edit. Cause - invalid data");
            model.addAttribute("cityList", mapService.getCityList());
            model.addAttribute("userList", userService.getUsersWithRole("Guest"));
            return "drivers/edit";
        } else {
            driverDTO.setOldNumber(pathNumber);
            driverService.updateDriver(driverDTO);
            redirectAttributes.addFlashAttribute("statusMsg", "Success. Driver #" + driverDTO.getNumber() + " was edit");
            return "redirect:/drivers";
        }
    }

    // ---------------------------------------- NEW ----------------------------------------

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newDriver(Model model) {
        model.addAttribute("driver", new DriverDTO());
        model.addAttribute("cityList", mapService.getCityList());
        model.addAttribute("userList", userService.getUsersWithRole("Guest"));
        return "drivers/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createDriver(
            @ModelAttribute("driver") @Valid DriverDTO driverDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMsg", "Failure. Couldn't add new driver. Cause - invalid data");
            System.out.println(driverDTO);
        } else {
            driverService.addDriver(driverDTO);
            model.addAttribute("statusMsg", "Success. Driver #" + driverDTO.getNumber() + " was added");
            model.addAttribute("driver", new DriverDTO());
        }
        model.addAttribute("cityList", mapService.getCityList());
        model.addAttribute("userList", userService.getUsersWithRole("Guest"));
        return "drivers/new";
    }

    // ---------------------------------------- DELETE ----------------------------------------

    @RequestMapping(value = "/delete/{number}", method = RequestMethod.GET)
    public String deleteDriverConfirmation(@PathVariable(value = "number") Long number,Model model) {
        // TODO: Sanity check for id before using service
        model.addAttribute("driver", driverService.getDriverByNumber(number));
        return "drivers/delete";
    }

    @RequestMapping(value = "/delete/{number}", method = RequestMethod.POST)
    public String deleteDriver(
            @PathVariable(value = "number") Long number, final RedirectAttributes redirectAttributes) {
        // TODO: Sanity check for id before using service
        boolean isDeleted = driverService.deleteDriver(number);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("statusMsg", "Success. Driver #" + number + " was deleted");
        } else {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. Driver #" + number + " wasn't deleted");
        }
        return "redirect:/drivers";
    }
}
