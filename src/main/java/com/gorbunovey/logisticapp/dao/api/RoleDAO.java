package com.gorbunovey.logisticapp.dao.api;

import com.gorbunovey.logisticapp.entity.RoleEntity;

import java.util.List;

public interface RoleDAO {
    RoleEntity getByName(String name);
    List<RoleEntity> getRoles();
}
