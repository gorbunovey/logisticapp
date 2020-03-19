package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.RoleEntity;

public interface RoleDAO {
    RoleEntity getByName(String name);
}
