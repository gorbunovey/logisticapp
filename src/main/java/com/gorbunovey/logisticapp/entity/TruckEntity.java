package com.gorbunovey.logisticapp.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "truck")
public class TruckEntity {

    @NotBlank
    @Size(min = 7, max = 7)
    @Id
    @Column(name = "REG_NUMBER", nullable = false)
    private String regNumber;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "CREW")
    private int crewSize;

    @NotNull
    @Min(value = 0)
    @Max(value = 100000)
    @Column(name = "CAPACITY")
    private int capacity;

    @NotNull
    @Column(name = "STATE")
    private boolean state;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "CITY_ID")
    private CityEntity city;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "truck")
    private Set<DriverEntity> drivers;

    public TruckEntity() {
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public Set<DriverEntity> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<DriverEntity> drivers) {
        this.drivers = drivers;
    }
}
