package com.gorbunovey.logisticapp.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "city")
public class City {

    @NotNull
    @Min(value = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "S_ID", nullable = false)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 45)
    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "city")
    private Set<Truck> trucks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "city")
    private Set<Driver> drivers;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "departurePoint")
////    private Set<Map> departures;
////
////    @OneToMany(fetch = FetchType.EAGER, mappedBy = "destinationPoint")
////    private Set<Map> destinations;


    public City() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(Set<Truck> trucks) {
        this.trucks = trucks;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    //    public Set<Map> getDepartures(){
//        return departures;
//    }
//
//    public void setDepartures(Set<Map> departures){
//        this.departures = departures;
//    }
//
//    public Set<Map> getDestinations(){
//        return destinations;
//    }
//
//    public void setDestinations(Set<Map> destinations){
//        this.destinations = destinations;
//    }

//    public void addDeparture(Map departure) {
//        departures.add(departure);
//        departure.setDeparturePoint(this);
//    }
//
//    public void removeDeparture(Map departure) {
//        departures.remove(departure);
//        departure.setDeparturePoint(null);
//    }
//
//    public void addDestination(Map destination) {
//        destinations.add(destination);
//        destination.setDestinationPoint(this);
//    }
//
//    public void removeDestination(Map destination) {
//        destinations.remove(destination);
//        destination.setDestinationPoint(null);
//    }


}
