package com.gorbunovey.logisticapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.*;

@Getter
@Setter
public class TruckDTO {

    @NotBlank
    @Size(min = 7, max = 7)
    private String regNumber;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private int crew;

    @NotNull
    private float capacity;

    @NotNull
    private boolean active;

    private Long cityCode;
    private String cityName;

}
