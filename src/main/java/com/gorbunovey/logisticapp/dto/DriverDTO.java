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

    public static String[] statuses = {"First-Driver", "Second-Driver", "Cargo-handling", "Resting"};
    public static String[] isOnShiftStatuses = {"not-on-shift", "on-shift"}; // must be only 2

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

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String userRoleName;
}
