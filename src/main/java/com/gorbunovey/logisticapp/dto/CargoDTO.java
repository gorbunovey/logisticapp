package com.gorbunovey.logisticapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CargoDTO {

    private long number;
    private String title;
    private int weight;
    private String status;

    private Long cityFromCode;
    private String cityFromName;
    private Long cityToCode;
    private String cityToName;
}
