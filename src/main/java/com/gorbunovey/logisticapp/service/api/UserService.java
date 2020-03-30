package com.gorbunovey.logisticapp.service.api;

import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.entity.UserEntity;

import java.util.List;

public interface UserService {
    void registerNewUser(UserDTO newUserDTO);
    UserDTO getUser(Long id);
    List<UserDTO> getUsers();
    List<UserDTO> getUsersWithRole(String role);
    void updateUser(UserDTO userDTO);
    boolean updateUserAndKeepRole(UserDTO userDTO);
    boolean updateUserRole(Long userId, String newRole);
    boolean deleteUser(Long id);
    UserEntity getUserEntity(Long id);
    UserDTO getUserByEmail(String email);
    List<String> getRoles();
}
