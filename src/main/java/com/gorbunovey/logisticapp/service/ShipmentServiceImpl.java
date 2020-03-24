package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.dto.TruckDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private CargoService cargoService;
    @Autowired
    private MapService mapService;

    @Override
    public int calculateDistanceWithOrdering(List<WayPointDTO> wayPoints, Long truckCityCode) {
        // Calculate distance, ordering seqNumber
        int totalDistance = 0;
        Long lastStopCityCode = truckCityCode;
        for(int i = 0; i< wayPoints.size(); i++){
            WayPointDTO wayPoint = wayPoints.get(i);
            wayPoint.setSeqNumber(i+1);
            // choose next stop
            Long newStopCityCode;
            if(wayPoint.isType()){
                newStopCityCode = wayPoint.getCargo().getCityFromCode();
            }else{
                newStopCityCode = wayPoint.getCargo().getCityToCode();
            }
            // calculate distance and add routeStop
            if(!newStopCityCode.equals(lastStopCityCode)){
                totalDistance += mapService.getDistanceBetween(lastStopCityCode, newStopCityCode);
                lastStopCityCode = newStopCityCode;
            }
        }
        return totalDistance;
    }

    @Override
    public int calculateMaxMass(List<WayPointDTO> wayPoints) {
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

    @Override
    public int calculateResultAccumulatedMass(List<WayPointDTO> wayPoints) {
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

    @Override
    public int calculateDriveTimeForOne(List<WayPointDTO> wayPoints, TruckDTO truck) {
        int distance = calculateDistanceWithOrdering(wayPoints, truck.getCityCode());
        // Calculate the drive time:
        // Average speed = 75 km/h
        final float speed = 75;
        float totalDriveTime = distance/speed;
        int driveTimeForOne = Math.round(totalDriveTime/truck.getCrew());
        return driveTimeForOne;
    }

    @Override
    public List<CargoDTO> getAvailableCargo(List<WayPointDTO> wayPoints) {
        // preparing available cargo for loading:
        if(wayPoints.isEmpty()){
            // shipment list empty? -> show all prepared cargo:
            return cargoService.getCargoWithStatusWithoutOrder("prepared");
        }else{
            WayPointDTO lastPoint = wayPoints.get(wayPoints.size()-1);
            List<CargoDTO> preparedCargo;
            // filter prepared cargo by city in order to last action - load or unload:
            if(lastPoint.isType()){
                // if loading - filter cargo by cityFrom:
                preparedCargo = cargoService.getCargoWithStatusFromCityWithoutOrder(lastPoint.getCargo().getCityFromCode(), "prepared");
            }else{
                // if unloading - filter cargo by cityTo:
                preparedCargo = cargoService.getCargoWithStatusFromCityWithoutOrder(lastPoint.getCargo().getCityToCode(), "prepared");
            }
            // get list of cargo which already added:
            List<Long> addedCargoNumbers = wayPoints.stream()
                    .map(p->p.getCargo().getNumber())
                    .collect(Collectors.toList());
            // remove those cargo from prepared:
            preparedCargo.removeIf(cargo->addedCargoNumbers.contains(cargo.getNumber()));
            return preparedCargo;
        }
    }

    @Override
    public boolean loadCargoToWayPoints(List<WayPointDTO> wayPoints, Long cargoNumber) {
        // Check if cargo is already present in wayPoints:
        WayPointDTO loadPoint = wayPoints.stream()
                .filter(WayPointDTO::isType)
                .filter(point->point.getCargo().getNumber() == cargoNumber)
                .findAny().orElse(null);
        if(loadPoint != null) return false;
        // Add new way point:
        CargoDTO cargo = cargoService.getCargoByNumber(cargoNumber);
        if (cargo != null){
            WayPointDTO newWayPoint = new WayPointDTO();
            newWayPoint.setType(true);
            newWayPoint.setCargo(cargo);
            cargo.setStatus("load"); // mark cargo as loaded
            wayPoints.add(newWayPoint);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean unLoadCargoToWayPoints(List<WayPointDTO> wayPoints, Long cargoNumber) {
        // Check if cargo is present in wayPoints already as unloaded cargo:
        WayPointDTO alreadyUnloaded = wayPoints.stream()
                .filter(point-> !point.isType())
                .filter(point-> point.getCargo().getNumber() == cargoNumber)
                .findAny().orElse(null);
        if(alreadyUnloaded != null) return false;
        // Check if cargo is present in wayPoints as loaded cargo:
        WayPointDTO loadPoint = wayPoints.stream()
                .filter(WayPointDTO::isType)
                .filter(point->point.getCargo().getNumber() == cargoNumber)
                .findAny().orElse(null);
        if(loadPoint == null){
            return false;
        }else{
            // Add new new way point:
            WayPointDTO newWayPoint = new WayPointDTO();
            newWayPoint.setType(false);
            newWayPoint.setCargo(loadPoint.getCargo());
            loadPoint.getCargo().setStatus("unload"); // mark cargo as unloaded
            wayPoints.add(newWayPoint);
            return true;
        }
    }

    @Override
    public void removeCargoFromWayPoints(List<WayPointDTO> wayPoints, int seqNumber) {
        // Sanity check:
        if(seqNumber >= 0 && !wayPoints.isEmpty() && seqNumber <= wayPoints.size()){
            WayPointDTO removableWayPoint = wayPoints.get(seqNumber-1);
            // Removing wayPoint:
            if(removableWayPoint.isType()){
                // when remove load position -> than need to remove unload position also:
                wayPoints.removeIf(p -> p.getCargo().getNumber() == removableWayPoint.getCargo().getNumber());
            }else{
                // otherwise -> just removing unload wayPoint:
                wayPoints.remove(seqNumber-1);
                // and need to change status:
                removableWayPoint.getCargo().setStatus("load");
            }
        }
    }
}
