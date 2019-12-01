package com.gorbunovey.logisticapp.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "truck")
public class Truck {

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
    private City city;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "truck")
    private Set<Driver> drivers;

    public Truck() {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }
}
