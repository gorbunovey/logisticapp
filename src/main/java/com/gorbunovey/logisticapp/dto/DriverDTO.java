package com.gorbunovey.logisticapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class DriverDTO {

    private Long id;

    @NotNull
    @Digits(integer=18, fraction=0)
    private long number;

    private Long cityCode; // уникальный идентификатор города
    private String cityName;

    @NotBlank
    @Size(min = 2, max = 45)
    private String userFirstName;
    @NotBlank
    @Size(min = 2, max = 45)
    private String userLastName;
    private Date userBirthday;
    private Long userNumber; // уникальный идентификатор юзера
    private String userEmail;
    private String userPassword;
    private String userRoleName; // нужен ли?

    // DriverEntity есть Set истории водилы:
    // поля ниже нужно вычислять из этого сета:
    // наверно лучше его сделать трисетом с макс датой на верху
    private String status; // как вычислить
    private int hours; //

}
