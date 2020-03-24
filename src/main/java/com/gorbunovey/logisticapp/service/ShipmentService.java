package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.CargoDTO;
import com.gorbunovey.logisticapp.dto.TruckDTO;
import com.gorbunovey.logisticapp.dto.WayPointDTO;

import java.util.List;

public interface ShipmentService {
    int calculateDistanceWithOrdering(List<WayPointDTO> wayPoints, Long truckCityCode) ;
    int calculateMaxMass(List<WayPointDTO> wayPoints);
    int calculateResultAccumulatedMass(List<WayPointDTO> wayPoints);
    int calculateDriveTimeForOne(List<WayPointDTO> wayPoints, TruckDTO truck);
    List<CargoDTO> getAvailableCargo(List<WayPointDTO> wayPoints);
    boolean loadCargoToWayPoints(List<WayPointDTO> wayPoints, Long cargoNumber);
    boolean unLoadCargoToWayPoints(List<WayPointDTO> wayPoints, Long cargoNumber);
    void removeCargoFromWayPoints(List<WayPointDTO> wayPoints, int seqNumber);
}
