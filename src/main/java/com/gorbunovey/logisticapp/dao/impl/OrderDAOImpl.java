package com.gorbunovey.logisticapp.dao.impl;

import com.gorbunovey.logisticapp.dao.api.OrderDAO;
import com.gorbunovey.logisticapp.entity.OrderEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long add(OrderEntity entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public OrderEntity get(Long id) {
        return entityManager.find(OrderEntity.class, id);
    }

    @Override
    public void update(OrderEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(OrderEntity entity) {
        entityManager.remove(entityManager.merge(entity));//merge for cascade entities
    }

    @Override
    public List<OrderEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM OrderEntity e").getResultList();
    }

    @Override
    public List<OrderEntity> getAllActive() {
        return entityManager.createQuery(
                "SELECT e FROM OrderEntity e WHERE e.active  = TRUE")
                .getResultList();
    }

    @Override
    public OrderEntity getActiveByDriverNumber(Long driverNumber) {
        TypedQuery<OrderEntity> q = entityManager.createQuery(
                "SELECT o FROM OrderEntity o JOIN o.drivers d WHERE o.active = TRUE AND d.number =  :driverNumber ", OrderEntity.class);
                        //"AND :driverNumber IN ( SELECT d.number FROM d)", OrderEntity.class);
        q.setParameter("driverNumber", driverNumber);
        return q.getResultStream().findAny().orElse(null);
    }
}
