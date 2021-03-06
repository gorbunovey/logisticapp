package com.gorbunovey.logisticapp.dao.impl;

import com.gorbunovey.logisticapp.dao.api.DriverDAO;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class DriverDAOImpl implements DriverDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(DriverEntity entity) {
        entityManager.persist(entity);
    }

    @Override
    public DriverEntity get(Long id) {
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
        return entityManager.createQuery("SELECT e FROM DriverEntity e").getResultList();
    }

    @Override
    public DriverEntity getByNumber(Long number) {
        TypedQuery<DriverEntity> q = entityManager.createQuery(
                "SELECT e FROM DriverEntity e WHERE e.number =: number", DriverEntity.class);
        q.setParameter("number", number);
        return q.getResultStream().findAny().orElse(null);
    }

    @Override
    public List<DriverEntity> getAllInCityWithoutOrder(Long cityCode) {
        TypedQuery<DriverEntity> q = entityManager.createQuery(
                "SELECT DISTINCT d FROM DriverEntity d LEFT JOIN d.orders o " +
                        "WHERE (d.city.code = :cityCode )" +
                        "AND ( o.active IN (FALSE) OR d.orders IS EMPTY)", DriverEntity.class);
        q.setParameter("cityCode", cityCode);
        return q.getResultList();
    }
}
