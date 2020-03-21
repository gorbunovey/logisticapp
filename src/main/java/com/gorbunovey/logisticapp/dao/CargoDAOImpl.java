package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.CargoEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        return entityManager.createQuery("SELECT e FROM CargoEntity e").getResultList();
    }

    @Override
    public CargoEntity getByNumber(Long number) {
        TypedQuery<CargoEntity> q = entityManager.createQuery(
                "SELECT e from CargoEntity e where e.number =: number", CargoEntity.class);
        q.setParameter("number", number);
        return q.getResultStream().findAny().orElse(null);
    }

    @Override
    public List<CargoEntity> findWithStatus(String status) {
        return entityManager.createQuery(
                "SELECT e FROM CargoEntity e WHERE e.status LIKE :status")
                .setParameter("status", status)
                .getResultList();
    }
}
