package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.dto.TruckDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;
import com.gorbunovey.logisticapp.service.DriverService;
import com.gorbunovey.logisticapp.service.OrderService;
import com.gorbunovey.logisticapp.service.ShipmentService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
// SessionScope!!! Проверить на инкогнито
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TruckService truckService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private DriverService driverService;

    // ----------------------------------------SHOW ORDER LIST ----------------------------------------

    @GetMapping
    public String getOrderList(Model model) {
        model.addAttribute("orderList", orderService.getOrderList());
        return "orders/orders";
    }

    // ---------------------------------------- NEW ORDER ----------------------------------------

    // Enter point
    @GetMapping(value = "/new")
    public String newOrder(Model model, HttpSession session) {
        List<WayPointDTO> wayPoints = new ArrayList<>();
        session.setAttribute("wayPoints", wayPoints);
        return "redirect:/orders/new/shipment";
    }

    // Show confirmation info
    @PostMapping(value = "/new")
    public String newOrderConfirmation() {
        return "orders/new";
    }

    // Add new order to DB
    @PostMapping(value = "/new/add")
    public String newOrderAdd(HttpSession session) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        TruckDTO truck = (TruckDTO) session.getAttribute("chosenTruck");
        List<DriverDTO> chosenDrivers = (List<DriverDTO>) session.getAttribute("chosenDrivers");
        orderService.addOrder(wayPoints, truck, chosenDrivers);
        session.removeAttribute("wayPoints");
        session.removeAttribute("chosenTruck");
        session.removeAttribute("chosenDrivers");
        return "redirect:/orders";
    }


    // ---------------------------------------- SHIPMENT (STEP 1) ----------------------------------------

    // Prepare cargo for view
    @GetMapping(value = "/new/shipment")
    public String newOrderShipment(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // Sanity check:
        if (wayPoints == null) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. Couldn't create a shipment list");
            return "redirect:/orders";
        }
        model.addAttribute("cargoList", shipmentService.getAvailableCargo(wayPoints));
        return "orders/shipment";
    }

    // Add cargo as load point
    @PostMapping(value = "/new/shipment/{pathCargoNumber}", params = "load")
    public String newOrderShipmentLoad(@PathVariable(value = "pathCargoNumber") Long pathCargoNumber,
                                       HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        boolean isLoaded = shipmentService.loadCargoToWayPoints(wayPoints, pathCargoNumber);
        if (!isLoaded) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't add Cargo #" + pathCargoNumber + " to load point");
        }
        return "redirect:/orders/new/shipment";
    }

    // Add cargo as unload point
    @PostMapping(value = "/new/shipment/{pathCargoNumber}", params = "unload")
    public String newOrderShipmentUnload(@PathVariable(value = "pathCargoNumber") Long pathCargoNumber,
                                         HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        boolean isUnLoaded = shipmentService.unLoadCargoToWayPoints(wayPoints, pathCargoNumber);
        if (!isUnLoaded) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't add unload point. Please, load this cargo at first");
        }
        return "redirect:/orders/new/shipment";
    }

    // Remove way point
    @PostMapping(value = "/new/shipment/{pathSeqNumber}", params = "delete")
    public String newOrderShipmentDelete(@PathVariable(value = "pathSeqNumber") int pathSeqNumber,
                                         HttpSession session) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        shipmentService.removeCargoFromWayPoints(wayPoints, pathSeqNumber);
        return "redirect:/orders/new/shipment";
    }

    // ---------------------------------------- TRUCKS (STEP 2) ----------------------------------------

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
        if (shipmentService.calculateResultAccumulatedMass(wayPoints) != 0) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't create a shipment list. Cause - loading and unloading mismatch");
            return "redirect:/orders/new/shipment";
        }
        int maxMass = shipmentService.calculateMaxMass(wayPoints);
        session.setAttribute("maxMass", maxMass);
        model.addAttribute("trucks", truckService.getAllActiveWithCapacityAndFree(maxMass / 1000f));
        return "orders/trucks";
    }

    @PostMapping(value = "/new/trucks/{pathRegNumber}")
    public String newOrderAddTruck(@PathVariable(value = "pathRegNumber") String pathRegNumber,
                                   HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        TruckDTO truckDTO = truckService.getTruckByRegNumber(pathRegNumber);
        if (truckDTO == null) {
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't set a truck with registration number #" + pathRegNumber);
            return "redirect:/orders/new/trucks";
        } else {
            session.setAttribute("chosenTruck", truckDTO);
        }
        return "redirect:/orders/new/drivers";
    }

    // ---------------------------------------- DRIVERS (STEP 3) ----------------------------------------

    // Show suitable trucks
    @GetMapping(value = "/new/drivers")
    public String newOrderDrivers(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        TruckDTO truck = (TruckDTO) session.getAttribute("chosenTruck");
        List<DriverDTO> chosenDrivers = (List<DriverDTO>) session.getAttribute("chosenDrivers");
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
        if (chosenDrivers == null){
            chosenDrivers = new ArrayList<>();
            session.setAttribute("chosenDrivers" , chosenDrivers);
        }
        int driveHours = shipmentService.calculateDriveTimeForOne(wayPoints, truck);
        List<DriverDTO> availableDrivers = driverService.GetAllFreeInCityWithHours(truck.getCityCode(), driveHours);
        driverService.filterDriverList(availableDrivers, chosenDrivers);
        model.addAttribute("driverList", availableDrivers);
        return "orders/drivers";
    }

    @PostMapping(value = "/new/drivers/{pathNumber}")
    public String newOrderAddDriver(@PathVariable(value = "pathNumber") long pathNumber, HttpSession session) {
        TruckDTO truck = (TruckDTO) session.getAttribute("chosenTruck");
        List<DriverDTO> chosenDrivers = (List<DriverDTO>) session.getAttribute("chosenDrivers");
        chosenDrivers.add(driverService.getDriverByNumber(pathNumber));
        if(chosenDrivers.size() < truck.getCrew()){
            return "redirect:/orders/new/drivers";
        }else{
            return "orders/new";
        }
    }

    // Remove chosen driver
    @PostMapping(value = "/new/drivers/delete/{pathNumber}")
    public String newOrderDeleteDriver(@PathVariable(value = "pathNumber") long pathNumber,
                                         HttpSession session) {
        List<DriverDTO> chosenDrivers = (List<DriverDTO>) session.getAttribute("chosenDrivers");
        chosenDrivers.removeIf(driver -> driver.getNumber() == pathNumber);
        return "redirect:/orders/new/drivers";
    }
}
