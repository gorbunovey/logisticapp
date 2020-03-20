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
    private int seqNumber; // порядковый номер в заказе
    private boolean type;
    private CargoDTO cargo;

}
