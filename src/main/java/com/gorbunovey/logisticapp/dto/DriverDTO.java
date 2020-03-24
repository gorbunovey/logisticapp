package com.gorbunovey.logisticapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DriverDTO {

    @NotNull
    @Digits(integer=18, fraction=0)
    private long number;
    private long oldNumber;

    private Long cityCode;
    private String cityName;
    private String status;
    private int hours;
    private boolean onShift;
    private LocalDateTime lastShiftTime;

    private String userFirstName;
    private String userLastName;
    private Long userNumber;
    private String userEmail;
    private String userPassword;
    private String userRoleName;
}
