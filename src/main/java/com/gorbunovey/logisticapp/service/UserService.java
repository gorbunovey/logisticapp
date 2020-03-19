package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserDTO getUserByNumber(Long number);
    List<UserDTO> getUsers();
    List<UserDTO> getUsersWithRole(String role);
    boolean updateUser(UserDTO userDTO);
    boolean updateUserRole(Long userNumber, String newRole);
    UserEntity getUserEntityByNumber(Long number);
}
