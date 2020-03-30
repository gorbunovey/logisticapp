package com.gorbunovey.logisticapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WayPointDTO {

    private Long orderNumber;
    private int seqNumber; // sequence number in the order
    private boolean type;
    private CargoDTO cargo;

}
