package com.gorbunovey.logisticapp.dao.api;

import com.gorbunovey.logisticapp.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    void add(UserEntity entity);
    UserEntity get(Long id);
    void update(UserEntity entity);
    void delete(UserEntity entity);
    List<UserEntity> getAll();
    List<UserEntity> getAllWithRole(String roleName);
    UserEntity getByEmail(String email);
    boolean isExistByEmail(String email);
}
