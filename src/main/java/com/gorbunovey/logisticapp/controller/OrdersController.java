package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;
import com.gorbunovey.logisticapp.service.CargoService;
import com.gorbunovey.logisticapp.service.OrderService;
import com.gorbunovey.logisticapp.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
// SessionScope!!! Проверить на инкогнито
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private TruckService truckService;

    // ---------------------------------------- ALL ----------------------------------------

    @GetMapping
    public String getOrderList(Model model) {
        model.addAttribute("orderList", orderService.getOrderList());
        return "orders/orders";
    }

    // ---------------------------------------- NEW ----------------------------------------

    @GetMapping(value = "/new")
    public String newOrder(Model model, HttpSession session) {
        List<WayPointDTO> wayPoints = new ArrayList<>();
        session.setAttribute("wayPoints", wayPoints);
        return "redirect:/orders/new/shipment";
    }
    // @PostMapping -> for last operation of making order, where will be some last checks

    // ---------------------------------------- SHIPMENT ----------------------------------------

    // Prepare cargo for view
    @GetMapping(value = "/new/shipment")
    public String newOrderShipment(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // Sanity check:
        if(wayPoints == null){
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. Couldn't create a shipment list");
            return "redirect:/orders";
        }
        // preparing available cargo for loading:
        if(wayPoints.isEmpty()){
            // shipment list empty? -> show all prepared cargo:
            model.addAttribute("cargoList", cargoService.getCargoWithStatus("prepared"));
        }else{
            WayPointDTO lastPoint = wayPoints.get(wayPoints.size()-1);
            List<CargoDTO> preparedCargo;
            // filter prepared cargo by city in order to last action - load or unload:
            if(lastPoint.isType()){
                // if loading - filter cargo by cityFrom:
                preparedCargo = getCargoListFromCity(lastPoint.getCargo().getCityFromCode());
            }else{
                // if unloading - filter cargo by cityTo:
                preparedCargo = getCargoListFromCity(lastPoint.getCargo().getCityToCode());
            }
            // get list of cargo which already added:
            List<Long> addedCargoNumbers = wayPoints.stream()
                    .map(p->p.getCargo().getNumber())
                    .collect(Collectors.toList());
            // remove those cargo from prepared:
            preparedCargo.removeIf(cargo->addedCargoNumbers.contains(cargo.getNumber()));
            model.addAttribute("cargoList", preparedCargo);
        }
        return "orders/shipment";
    }

    // Add cargo as load point
    @PostMapping(value = "/new/shipment/{pathCargoNumber}", params = "load")
    public String newOrderShipmentLoad(@PathVariable(value = "pathCargoNumber") Long pathCargoNumber, Model model,
                                       HttpSession session, final RedirectAttributes redirectAttributes) {
        // TODO: check for null for session objects and pathCargoNumber, if problems - redirect
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // Check if cargo is already present in wayPoints:
        WayPointDTO loadPoint = wayPoints.stream()
                .filter(WayPointDTO::isType)
                .filter(point->point.getCargo().getNumber() == pathCargoNumber)
                .findAny().orElse(null);
        if(loadPoint != null) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't add load point. Cargo already loaded");
            return "redirect:/orders/new/shipment";
        }
        // Add new way point:
        CargoDTO cargo = cargoService.getCargoByNumber(pathCargoNumber);
        if (cargo != null){
            WayPointDTO newWayPoint = new WayPointDTO();
            newWayPoint.setType(true);
            newWayPoint.setCargo(cargo);
            cargo.setStatus("load"); // mark cargo as loaded
            wayPoints.add(newWayPoint);
        }else{
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't add cargo with number#"+ pathCargoNumber);
        }
        return "redirect:/orders/new/shipment";
    }

    // Add cargo as unload point
    @PostMapping(value = "/new/shipment/{pathCargoNumber}", params = "unload")
    public String newOrderShipmentUnload(@PathVariable(value = "pathCargoNumber") Long pathCargoNumber, Model model,
                                         HttpSession session, final RedirectAttributes redirectAttributes) {
        // TODO: check for null for session objects and pathCargoNumber, if problems - redirect
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // Check if cargo is present in wayPoints already as unloaded cargo:
        WayPointDTO alreadyUnloaded = wayPoints.stream()
                .filter(point-> !point.isType())
                .filter(point->point.getCargo().getNumber() == pathCargoNumber)
                .findAny().orElse(null);
        if(alreadyUnloaded != null){
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't add unload point. Cargo already unloaded");
            return "redirect:/orders/new/shipment";
        }
        // Check if cargo is present in wayPoints as loaded cargo:
        WayPointDTO loadPoint = wayPoints.stream()
                .filter(WayPointDTO::isType)
                .filter(point->point.getCargo().getNumber() == pathCargoNumber)
                .findAny().orElse(null);
        if(loadPoint == null){
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't add unload point. Please load this cargo at first");
        }else{
            // Add new new way point:
            WayPointDTO newWayPoint = new WayPointDTO();
            newWayPoint.setType(false);
            newWayPoint.setCargo(loadPoint.getCargo());
            loadPoint.getCargo().setStatus("unload"); // mark cargo as unloaded
            wayPoints.add(newWayPoint);
        }
        return "redirect:/orders/new/shipment";
    }

    // Remove way point
    @PostMapping(value = "/new/shipment/{pathSeqNumber}", params = "delete")
    public String newOrderShipmentDelete(@PathVariable(value = "pathSeqNumber") int pathSeqNumber, Model model,
                                         HttpSession session, final RedirectAttributes redirectAttributes) {
        // TODO: check for null for session objects and pathCargoNumber, if problems - redirect
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");

        // Sanity check:
        if(pathSeqNumber >= 0 && !wayPoints.isEmpty() && pathSeqNumber <= wayPoints.size()){
            WayPointDTO removableWayPoint = wayPoints.get(pathSeqNumber-1);
            // Removing wayPoint:
            if(removableWayPoint.isType()){
                // when remove load position -> than need to remove unload position also:
                wayPoints.removeIf(p -> p.getCargo().getNumber() == removableWayPoint.getCargo().getNumber());
            }else{
                // otherwise -> just removing unload wayPoint:
                wayPoints.remove(pathSeqNumber-1);
                // and need to change status:
                removableWayPoint.getCargo().setStatus("load");
            }
        }
        return "redirect:/orders/new/shipment";
    }

    // ---------------------------------------- Trucks ----------------------------------------

    @GetMapping(value = "/new/trucks")
    public String newOrderTrucks(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // Sanity check:
        if (wayPoints == null) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. Couldn't create a shipment list");
            return "redirect:/orders";
        }
        // TODO: check wayPoints by calculating masses

        int maxMass = calculateMaxMass(wayPoints);
        session.setAttribute("maxMass", maxMass);
        System.out.println("--@@@@@@@@----------Controller------------getAllActiveWithCapacityAndFree ");

        model.addAttribute("trucks", truckService.getAllActiveWithCapacityAndFree(maxMass/1000f));
        System.out.println("--@@@@@@@@----------Controller------------getAllActiveWithCapacity ");
        truckService.getAllActiveWithCapacity(maxMass/1000f);
        return "orders/trucks";

    }
    // ---------------------------------------- PRIVATE METHODS ----------------------------------------

    private int calculateMaxMass(List<WayPointDTO> wayPoints){
        int maxMass = 0;
        int accumulatedMass = 0;
        for( WayPointDTO p: wayPoints){
            if(p.isType()){
                accumulatedMass+=p.getCargo().getWeight();
            }else{
                accumulatedMass-=p.getCargo().getWeight();
            }
            if(accumulatedMass > maxMass) maxMass = accumulatedMass;
        }
        return maxMass;
    }

    private int calculateResultAccumulatedMass(List<WayPointDTO> wayPoints){
        int resultAccumulatedMass = 0;
        for( WayPointDTO p: wayPoints){
            if(p.isType()){
                resultAccumulatedMass+=p.getCargo().getWeight();
            }else{
                resultAccumulatedMass-=p.getCargo().getWeight();
            }
        }
        return resultAccumulatedMass;
    }

    // TODO: make a services instead
    private List<CargoDTO>  getCargoListFromCity(Long cityCode){
        List<CargoDTO> preparedCargo = cargoService.getCargoWithStatus("prepared");
        return preparedCargo.stream()
                .filter(c->c.getCityFromCode().equals(cityCode))
                .collect(Collectors.toList());
    }

}
