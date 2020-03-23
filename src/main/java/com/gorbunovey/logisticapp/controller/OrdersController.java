package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.dto.TruckDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;
import com.gorbunovey.logisticapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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
    @Autowired
    private MapService mapService;
    @Autowired
    private DriverService driverService;

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

    // ---------------------------------------- TRUCKS ----------------------------------------

    // Show suitable trucks
    @GetMapping(value = "/new/trucks")
    public String newOrderTrucks(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // Sanity checks:
        if (wayPoints == null) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't create a shipment list. Cause - shipment list is empty");
            return "redirect:/orders";
        }
        if(calculateResultAccumulatedMass(wayPoints) != 0){
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't create a shipment list. Cause - loading and unloading mismatch");
            return "redirect:/orders/new/shipment";
        }
        int maxMass = calculateMaxMass(wayPoints);
        session.setAttribute("maxMass", maxMass);
        model.addAttribute("trucks", truckService.getAllActiveWithCapacityAndFree(maxMass/1000f));
        return "orders/trucks";
    }

    @PostMapping(value = "/new/trucks/{pathRegNumber}")
    public String newOrderAddTruck(@PathVariable(value = "pathRegNumber") String pathRegNumber, Model model,
                                   HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // TODO: Sanity check(regexp) for regNumber before using service
        TruckDTO truckDTO = truckService.getTruckByRegNumber(pathRegNumber);
        if(truckDTO == null){
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't set a truck with registration number #" + pathRegNumber);
            return "redirect:/orders/new/trucks";
        }else{
            session.setAttribute("chosenTruck", truckDTO);
        }
        return "redirect:/orders/new/drivers";
    }

    // ---------------------------------------- DRIVERS ----------------------------------------

    // Show suitable trucks
    @GetMapping(value = "/new/drivers")
    public String newOrderDrivers(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        TruckDTO truck = (TruckDTO) session.getAttribute("chosenTruck");
        // Sanity check:
        if (wayPoints == null) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't create a shipment list. Cause - shipment list is empty");
            return "redirect:/orders";
        }
        if (truck == null) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't set a truck. Please, choose the suitable truck");
            return "redirect:/orders/new/trucks";
        }
        // Calculate distance, ordering seqNumber, route (for better visualization)
        int totalDistance = 0;
        Long lastStopCityCode = truck.getCityCode();
        //List<String> route = new ArrayList<>();// for visualization
        //route.add(truck.getCityName());// for visualization
        for(int i = 0; i< wayPoints.size(); i++){
            WayPointDTO wayPoint = wayPoints.get(i);
            wayPoint.setSeqNumber(i+1);
            // choose next stop
            Long newStopCityCode;
            //String newStopCityName;// for visualization
            if(wayPoint.isType()){
                newStopCityCode = wayPoint.getCargo().getCityFromCode();
                //newStopCityName = wayPoint.getCargo().getCityFromName();// for visualization
            }else{
                newStopCityCode = wayPoint.getCargo().getCityToCode();
                //newStopCityName = wayPoint.getCargo().getCityToName();// for visualization
            }
            // calculate distance and add routeStop
            if(!newStopCityCode.equals(lastStopCityCode)){
                totalDistance += mapService.getDistanceBetween(lastStopCityCode, newStopCityCode);
                lastStopCityCode = newStopCityCode;
                //route.add(newStopCityName);// for visualization
            }
        }
        //model.addAttribute("routeList", route);// for visualization
        // Calculate the journey time:
        // Average speed = 75 km/h
        // Driver's daily shift = 9 hours
        final double speed = 75;
        final double shift = 9;
        double driveTime = totalDistance/speed;
        double shiftsNeed = driveTime/shift;
        double daysNeed = shiftsNeed/truck.getCrew();
        double hoursUnderway = Math.ceil(daysNeed*24);
        System.out.println("---------driveTime----------->" + driveTime);
        System.out.println("---------driveTime----------->" + driveTime);
        System.out.println("---------shiftsNeed----------->" + shiftsNeed);
        System.out.println("---------daysNeed----------->" + daysNeed);
        System.out.println("---------hoursUnderway----------->" + hoursUnderway);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfNextMonth = LocalDateTime.of(now.toLocalDate().with(TemporalAdjusters.firstDayOfNextMonth()), LocalTime.MIDNIGHT);
        System.out.println("---------now----------->" + now);
        System.out.println("---------firstDayOfNextMonth----------->" + firstDayOfNextMonth);
        long firstWay = now.until(firstDayOfNextMonth, ChronoUnit.HOURS);
        long secondWay = ChronoUnit.HOURS.between(now,firstDayOfNextMonth);
        System.out.println("---------now.until(firstDayOfNextMonth, ChronoUnit.HOURS)----------->" + firstWay);
        System.out.println("---------ChronoUnit.HOURS.between(now,firstDayOfNextMonth)----------->" + secondWay);


        // проверка ДАО
        driverService.GetAllFreeInCityWithHours(truck.getCityCode(), 0);







        return "orders/drivers";

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
