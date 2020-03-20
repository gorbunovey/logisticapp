package com.gorbunovey.logisticapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderDTO {

    private long number;
    private boolean active;
    private String truckRegNumber;
    //List<DriverDTO> drivers; // возможно просто список номеров водил?
    List<Long> driversNumber;
    List<WayPointDTO> wayPoints;

}
