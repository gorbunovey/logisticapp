package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.entity.UserEntity;

import java.util.List;

public interface UserService {
    void registerNewUser(UserDTO newUserDTO);
    UserDTO getUser(Long id);
    List<UserDTO> getUsers();
    List<UserDTO> getUsersWithRole(String role);
    boolean updateUser(UserDTO userDTO);
    boolean updateUserRole(Long userId, String newRole);
    UserEntity getUserEntity(Long id);
    UserDTO getUserByEmail(String email);
}
