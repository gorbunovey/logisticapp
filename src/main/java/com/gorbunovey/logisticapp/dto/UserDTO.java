package com.gorbunovey.logisticapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDTO {

    private Long number;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roleName;
}
