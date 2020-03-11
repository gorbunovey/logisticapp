package com.gorbunovey.logisticapp.dto;

import javax.validation.constraints.*;
import java.util.Set;

public class TruckDTO {

    @NotBlank
    @Size(min = 7, max = 7)
    private String regNumber;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private int crewSize;

    @NotNull
    @Min(value = 0)
    @Max(value = 100000)
    private int capacity;

    @NotNull
    private boolean state;

    private CityDTO city;

    private Set<DriverDTO> drivers;

    public TruckDTO() {
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

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public Set<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<DriverDTO> drivers) {
        this.drivers = drivers;
    }
}
