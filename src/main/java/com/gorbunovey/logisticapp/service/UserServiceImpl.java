package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.RoleDAO;
import com.gorbunovey.logisticapp.dao.UserDAO;
import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO getUserByNumber(Long number) {
        UserEntity userEntity = userDAO.getByNumber(number);
        if (userEntity == null) {
            return null;
        } else {
            return modelMapper.map(userEntity, UserDTO.class);
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        List<UserDTO> userDTOList = new ArrayList<>();
        userDAO.getAll().forEach(userEntity -> userDTOList.add(modelMapper.map(userEntity, UserDTO.class)));
        return userDTOList;
    }

    @Override
    public List<UserDTO> getUsersWithRole(String role) {
        List<UserDTO> usersWithRoleDTOList = new ArrayList<>();
        userDAO.FindWithRole(role).forEach(userEntity -> usersWithRoleDTOList.add(modelMapper.map(userEntity, UserDTO.class)));
        System.out.println("--------------------------" + usersWithRoleDTOList.isEmpty());
        usersWithRoleDTOList.forEach(System.out::println);
        return usersWithRoleDTOList;
    }

    @Override
    @Transactional
    public boolean updateUser(UserDTO userDTO) {
        UserEntity userEntity = userDAO.getByNumber(userDTO.getNumber());
        if (userEntity == null) {
            return false;
        } else {
            userEntity.setLastName(userDTO.getLastName());
            userEntity.setFirstName(userDTO.getFirstName());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setPassword(userDTO.getPassword());
            userEntity.setRole(roleDAO.getByName(userDTO.getRoleName()));
            userDAO.update(userEntity);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean updateUserRole(Long userNumber, String newRole) {
        UserEntity userEntity = userDAO.getByNumber(userNumber);
        if (userEntity == null) {
            return false;
        } else {
            userEntity.setRole(roleDAO.getByName(newRole));
            userDAO.update(userEntity);
            return true;
        }
    }

    @Override
    public UserEntity getUserEntityByNumber(Long number) {
        return userDAO.getByNumber(number);
    }
}
