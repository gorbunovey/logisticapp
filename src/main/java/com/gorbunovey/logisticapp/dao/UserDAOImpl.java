package com.gorbunovey.logisticapp.dao;

import com.gorbunovey.logisticapp.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void add(UserEntity entity) {
        entityManager.persist(entity);
    }

    @Override
    public UserEntity get(Long id) {
        return entityManager.find(UserEntity.class, id);
    }

    @Override
    public void update(UserEntity entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(UserEntity entity) {
        entityManager.remove(entityManager.merge(entity));//merge for cascade entities
    }

    @Override
    public List<UserEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM UserEntity e").getResultList();
    }

    @Override
    public UserEntity getByNumber(Long number) {
        TypedQuery<UserEntity> q = entityManager.createQuery(
                "SELECT e FROM UserEntity e WHERE e.number =: number", UserEntity.class);
        q.setParameter("number", number);
        return q.getResultStream().findAny().orElse(null);
    }

    @Override
    public List<UserEntity> FindWithRole(String roleName) {
        return entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.role.name LIKE :roleName")
                .setParameter("roleName", roleName)
                //.setMaxResults(10)
                .getResultList();
    }
}
