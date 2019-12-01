//package com.gorbunovey.logisticapp.model;
//
//import javax.persistence.*;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;
//
//@Entity
//@Table(name = "map")
//public class Map {
//
//    @NotNull
//    @Min(value = 0)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "S_ID", nullable = false)
//    private Long id;
//
//    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumn(name = "S_ID")
//    private City departurePoint;
//
//    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumn(name = "S_ID")
//    private City destinationPoint;
//
//    public Map() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public City getDeparturePoint() {
//        return departurePoint;
//    }
//
//    public void setDeparturePoint(City departurePoint) {
//        this.departurePoint = departurePoint;
//    }
//
//    public City getDestinationPoint() {
//        return destinationPoint;
//    }
//
//    public void setDestinationPoint(City destinationPoint) {
//        this.destinationPoint = destinationPoint;
//    }
//}
