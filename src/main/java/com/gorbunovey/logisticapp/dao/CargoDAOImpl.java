package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.CargoEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CargoDAOImpl implements CargoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(CargoEntity entity) {
        entityManager.persist(entity);
    }

    @Override
    public CargoEntity get(Long id) {
        return entityManager.find(CargoEntity.class, id);
    }

    @Override
    public void update(CargoEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(CargoEntity entity) {
        entityManager.remove(entityManager.merge(entity));//merge for cascade entities
    }

    @Override
    public List<CargoEntity> getAll() {
        return entityManager.createQuery("select e from CargoEntity e").getResultList();
    }
}
