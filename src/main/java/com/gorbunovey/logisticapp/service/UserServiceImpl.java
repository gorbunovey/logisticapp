package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.RoleDAO;
import com.gorbunovey.logisticapp.dao.UserDAO;
import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public void registerNewUser(UserDTO newUserDTO) {
        if(userDAO.isExistByEmail(newUserDTO.getEmail())){
            // throw custom exception - EmailExistsException
        }else{
            UserEntity newUserEntity = new UserEntity();
            newUserEntity.setFirstName(newUserDTO.getFirstName());
            newUserEntity.setLastName(newUserDTO.getLastName());
            newUserEntity.setEmail(newUserDTO.getEmail());
            newUserEntity.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
            newUserEntity.setRole(roleDAO.getByName("Guest"));
            userDAO.add(newUserEntity);
        }
    }

    @Override
    public UserDTO getUser(Long id) {
        UserEntity userEntity = userDAO.get(id);
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
        userDAO.getAllWithRole(role).forEach(userEntity -> usersWithRoleDTOList.add(modelMapper.map(userEntity, UserDTO.class)));
        return usersWithRoleDTOList;
    }

    @Override
    @Transactional
    public boolean updateUser(UserDTO userDTO) {
        UserEntity userEntity = userDAO.get(userDTO.getId());
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
    public boolean updateUserRole(Long userId, String newRole) {
        UserEntity userEntity = userDAO.get(userId);
        if (userEntity == null) {
            return false;
        } else {
            userEntity.setRole(roleDAO.getByName(newRole));
            userDAO.update(userEntity);
            return true;
        }
    }

    @Override
    public UserEntity getUserEntity(Long id) {
        return userDAO.get(id);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserEntity userEntity = userDAO.getByEmail(email);
        if (userEntity == null) {
            return null;
        } else {
            return modelMapper.map(userEntity, UserDTO.class);
        }
    }
}
