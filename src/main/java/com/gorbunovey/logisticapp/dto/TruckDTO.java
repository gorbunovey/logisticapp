package com.gorbunovey.logisticapp.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TruckDTO {

    @NotBlank
    @Size(min = 7, max = 7)
    private String regNumber;
    private String oldRegNumber;

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
