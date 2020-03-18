package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.TruckEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class TruckDAOImpl implements TruckDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(TruckEntity entity) {
        entityManager.persist(entity);
    }

    @Override
    public TruckEntity get(Long id) {
        return entityManager.find(TruckEntity.class, id);
    }

    @Override
    public void update(TruckEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(TruckEntity entity) {
        entityManager.remove(entityManager.merge(entity));//merge for cascade entities
    }

    @Override
    public List<TruckEntity> getAll() {
        return entityManager.createQuery("select e from TruckEntity e").getResultList();
    }

    @Override
    public TruckEntity getByRegNumber(String regNumber) {
        TypedQuery<TruckEntity> q = entityManager.createQuery(
                "SELECT e from TruckEntity e where e.regNumber =: regNumber", TruckEntity.class);
        q.setParameter("regNumber", regNumber);
        return q.getResultStream().findAny().orElse(null);
    }
}
