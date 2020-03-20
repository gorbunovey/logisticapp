package com.gorbunovey.logisticapp.dao;

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
    public void add(OrderEntity entity) {
        entityManager.persist(entity);
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
        return entityManager.createQuery("select e from OrderEntity e").getResultList();
    }

    @Override
    public OrderEntity getByNumber(Long number) {
        TypedQuery<OrderEntity> q = entityManager.createQuery(
                "SELECT e from OrderEntity e where e.number =: number", OrderEntity.class);
        q.setParameter("number", number);
        return q.getResultStream().findAny().orElse(null);
    }
}
