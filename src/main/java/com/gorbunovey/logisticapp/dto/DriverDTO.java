package com.gorbunovey.logisticapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DriverDTO {

    @NotNull
    @Digits(integer=18, fraction=0)
    private long number;
    private long oldNumber;

    private Long cityCode; // уникальный идентификатор города
    private String cityName;

    private String userFirstName;
    private String userLastName;
    private Long userNumber; // уникальный идентификатор юзера
    private String userEmail;
    private String userPassword;
    private String userRoleName; // нужен ли?

    // DriverEntity есть Set истории водилы:
    // поля ниже нужно вычислять из этого сета:
    // наверно лучше его сделать трисетом с макс датой на верху
    private String status; // как вычислить?
    private int hours; // как вычислить?

}
