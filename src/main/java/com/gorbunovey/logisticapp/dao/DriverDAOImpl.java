package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.DriverEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import com.gorbunovey.logisticapp.entity.DriverEntity;

@Component
public class DriverDAOImpl implements DriverDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(DriverEntity driver) {
        entityManager.persist(driver);
    }

    @Override
    public DriverEntity get(int id) {
        return entityManager.find(DriverEntity.class, id);
    }

    @Override
    public void update(DriverEntity entity) {
        entityManager.merge(entity);

    }

    @Override
    public void delete(DriverEntity entity) {
        entityManager.remove(entityManager.merge(entity));//merge for cascade entities
    }

    @Override
    public List<DriverEntity> getAll() {
        return entityManager.createQuery("select d from DriverEntity d").getResultList();
    }
}
