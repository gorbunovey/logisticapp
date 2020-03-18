package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.TruckDTO;
import com.gorbunovey.logisticapp.service.MapService;
import com.gorbunovey.logisticapp.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/trucks")
public class TrucksController {

    @Autowired
    private TruckService truckService;

    @Autowired
    private MapService mapService;

    // ---------------------------------------- ALL ----------------------------------------

    @RequestMapping(method = RequestMethod.GET)
    public String getTruckList(Model model) {
        model.addAttribute("trucks", truckService.getTruckList());
        return "trucks/trucks";
    }

// ---------------------------------------- EDIT ----------------------------------------

    @RequestMapping(value = "/edit/{regNumber}", method = RequestMethod.GET)
    public String getTruck(
            @PathVariable(value = "regNumber") String regNumber, Model model) {
        // TODO: Sanity check for regNumber, before using service
        model.addAttribute("truck", truckService.getTruckByRegNumber(regNumber));
        model.addAttribute("cityList", mapService.getCityList());
        return "trucks/edit";
    }

    @RequestMapping(value = "/edit/{regNumber}", method = RequestMethod.POST)
    public String setTruck(
            @ModelAttribute("truck") @Valid TruckDTO truckDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMsg", "Failure. Truck #" + truckDTO.getRegNumber() + " wasn't edit");
            model.addAttribute("cityList", mapService.getCityList());
        } else {
            truckService.updateTruck(truckDTO);
            model.addAttribute("statusMsg", "Success. Truck #" + truckDTO.getRegNumber() + " was edit");
            model.addAttribute("cityList", mapService.getCityList());
        }
        return "trucks/edit";
    }

    // ---------------------------------------- NEW ----------------------------------------

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTruck(Model model) {
        // TODO: populate model TruckDTO by nature default values
        model.addAttribute("truck", new TruckDTO());
        model.addAttribute("cityList", mapService.getCityList());
        return "trucks/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createTruck(
            @ModelAttribute("truck") @Valid TruckDTO truckDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMsg", "Failure. Couldn't add new truck. Cause - invalid data");
        } else {
            truckService.addTruck(truckDTO);
            model.addAttribute("statusMsg", "Success. Truck #" + truckDTO.getRegNumber() + " was added");
            model.addAttribute("truck", new TruckDTO());
            model.addAttribute("cityList", mapService.getCityList());
        }
        return "trucks/new";
    }
    // ---------------------------------------- DELETE ----------------------------------------

    @RequestMapping(value = "/delete/{regNumber}", method = RequestMethod.GET)
    public String deleteTruckConfirmation(@PathVariable(value = "regNumber") String regNumber, Model model) {
        // TODO: Sanity check for regNumber before using service
        model.addAttribute("truck", truckService.getTruckByRegNumber(regNumber));
        return "trucks/delete";
    }

    @RequestMapping(value = "/delete/{regNumber}", method = RequestMethod.POST)
    public String deleteTruck(
            @PathVariable(value = "regNumber") String regNumber, final RedirectAttributes redirectAttributes) {
        // TODO: Sanity check for regNumber before using service
        boolean isDeleted = truckService.deleteTruck(regNumber);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("statusMsg", "Success. Truck #" + regNumber + " was deleted");
        } else {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. Truck #" + regNumber + " wasn't deleted");
        }
        return "redirect:/trucks";
    }
}
