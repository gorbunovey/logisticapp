package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.RoleEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RoleEntity getByName(String name) {
        TypedQuery<RoleEntity> q = entityManager.createQuery(
                "SELECT e FROM RoleEntity e WHERE e.name =: name", RoleEntity.class);
        q.setParameter("name", name);
        return q.getResultStream().findAny().orElse(null);
    }
}
