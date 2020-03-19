package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    void add(UserEntity entity);
    UserEntity get(Long id);
    void update(UserEntity entity);
    void delete(UserEntity entity);
    List<UserEntity> getAll();

    UserEntity getByNumber(Long number);
    List<UserEntity> FindWithRole(String roleName);

}
