package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;
import com.gorbunovey.logisticapp.service.CargoService;
import com.gorbunovey.logisticapp.service.OrderService;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
// SessionScope!!! Проверить на инкогнито
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CargoService cargoService;

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
        session.setAttribute("accumulatedMass", 0);
        session.setAttribute("maxMass", 0);

        return "redirect:/orders/new/shipment";
    }
    // @PostMapping -> for last operation of making order, where will be some last checks

    // ---------------------------------------- SHIPMENT ----------------------------------------

    @GetMapping(value = "/new/shipment")
    public String newOrderShipment(Model model, HttpSession session, final RedirectAttributes redirectAttributes) {

        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        Integer accumulatedMass = (Integer) session.getAttribute("accumulatedMass");
        Integer maxMass = (Integer) session.getAttribute("maxMass");
        // Sanity check:
        if(wayPoints == null || accumulatedMass == null || maxMass == null){
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. Couldn't create a shipment list");
            return "redirect:/orders";
        }
        // preparing available cargo for loading:
        if(wayPoints.isEmpty()){
            // if shipment list empty - show all prepared cargo:
            model.addAttribute("cargoList", cargoService.getCargoWithStatus("prepared"));
        }else{
            // if list not empty - filter by action - load or unload:
            WayPointDTO lastPoint = wayPoints.get(wayPoints.size()-1);
            if(lastPoint.isType()){
                // if loading - filter cargo by cityFrom:
                model.addAttribute("cargoList", getCargoListFromCity(lastPoint.getCargo().getCityFromCode()));
            }else{
                // if unloading - filter cargo by cityTo:
                model.addAttribute("cargoList", getCargoListFromCity(lastPoint.getCargo().getCityToCode()));

            }
        }
        return "orders/shipment";
    }

    @PostMapping(value = "/new/shipment/{pathCargoNumber}", params = "load")
    public String newOrderShipmentLoad(@PathVariable(value = "pathCargoNumber") Long pathCargoNumber, Model model,
                                       HttpSession session, final RedirectAttributes redirectAttributes) {
        // TODO: check for null for session objects and pathCargoNumber, if problems - redirect
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        // Add new way point:
        CargoDTO cargo = cargoService.getCargoByNumber(pathCargoNumber);
        if (cargo != null){
            WayPointDTO newWayPoint = new WayPointDTO();
            newWayPoint.setType(true);
            newWayPoint.setCargo(cargo);
            wayPoints.add(newWayPoint);
            // refresh mass values:
            int accumulatedMass = (int) session.getAttribute("accumulatedMass") + cargo.getWeight();
            session.setAttribute("accumulatedMass", accumulatedMass);
            int maxMass = (int) session.getAttribute("maxMass");
            if(accumulatedMass > maxMass) session.setAttribute("maxMass", accumulatedMass);

        }else{
            redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                    "Couldn't add cargo with number#"+ pathCargoNumber);
        }
        return "redirect:/orders/new/shipment";
    }

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
            CargoDTO cargo = cargoService.getCargoByNumber(pathCargoNumber);
            if (cargo != null){
                WayPointDTO newWayPoint = new WayPointDTO();
                newWayPoint.setType(false);
                newWayPoint.setCargo(cargo);
                wayPoints.add(newWayPoint);
                // refresh mass value:
                Integer accumulatedMass = (Integer) session.getAttribute("accumulatedMass") + cargo.getWeight();
                session.setAttribute("accumulatedMass", accumulatedMass);
            }else{
                redirectAttributes.addFlashAttribute("statusMsg", "Failure. " +
                        "Couldn't add cargo with number#"+ pathCargoNumber);
            }
        }
        return "redirect:/orders/new/shipment";
    }

    // ПОКА ОТЛОЖИЛ РЕАЛИЗАЦИЮ УДАЛЕНИЯ ПОЗИЦИИ!!!
    @PostMapping(value = "/new/shipment/{pathSeqNumber}", params = "delete")
    public String newOrderShipmentDelete(@PathVariable(value = "pathSeqNumber") int pathSeqNumber, Model model,
                                         HttpSession session, final RedirectAttributes redirectAttributes) {
        // TODO: check for null for session objects and pathCargoNumber, if problems - redirect
        List<WayPointDTO> wayPoints = (List<WayPointDTO>) session.getAttribute("wayPoints");
        //wayPoints.removeIf(point -> point.getSeqNumber() == pathSeqNumber);

        // Если удаляем загрузку, то нужно удалять и отгрузку!!!
        // НО если удаляем отгрузку, то загрузку нужно оставить
        // Пока реализовано только полностью удаление груза, присем без пересчета массы
        // Пока решил отложить реализацию удаления
        if(pathSeqNumber >= 0 && wayPoints != null && !wayPoints.isEmpty() && pathSeqNumber <= wayPoints.size()){
            WayPointDTO wayPoint = wayPoints.get(pathSeqNumber);
            Long cargoNumber = wayPoint.getCargo().getNumber();
            List<WayPointDTO> deletePoints = wayPoints.stream()
                    .filter(point->point.getCargo().getNumber() == cargoNumber)
                    .collect(Collectors.toList());
            // TODO: recalculate mass before deleting
            wayPoints.removeAll(deletePoints);
        }
        return "redirect:/orders/new/shipment";
    }


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
