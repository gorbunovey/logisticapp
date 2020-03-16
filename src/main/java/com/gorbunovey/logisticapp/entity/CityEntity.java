package com.gorbunovey.logisticapp.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"departurePoints", "destinationPoints", "drivers", "trucks", "citiesFrom", "citiesTo"})
@ToString(exclude = {"departurePoints", "destinationPoints", "drivers", "trucks", "citiesFrom", "citiesTo"})
@Entity
@Table(name = "CITY")
public class CityEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true)
    private long code;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "departurePoint")
    private Set<MapEntity> departurePoints;

    @OneToMany(mappedBy = "destinationPoint")
    private Set<MapEntity> destinationPoints;

    @OneToMany(mappedBy = "city")
    private Set<DriverEntity> drivers;

    @OneToMany(mappedBy = "city")
    private Set<TruckEntity> trucks;

    @OneToMany(mappedBy = "cityFrom")
    private Set<CargoEntity> citiesFrom;

    @OneToMany(mappedBy = "cityTo")
    private Set<CargoEntity> citiesTo;
}
