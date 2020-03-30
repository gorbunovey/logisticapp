package com.gorbunovey.logisticapp.dao.impl;

import com.gorbunovey.logisticapp.dao.api.UserDAO;
import com.gorbunovey.logisticapp.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
    public List<UserEntity> getAllWithRole(String roleName) {
        return entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.role.name LIKE :roleName")
                .setParameter("roleName", roleName)
                //.setMaxResults(10)
                .getResultList();
    }

    @Override
    public UserEntity getByEmail(String email) {
        TypedQuery<UserEntity> q = entityManager.createQuery(
                "SELECT e FROM UserEntity e WHERE e.email =: email", UserEntity.class);
        q.setParameter("email", email);
        return q.getResultStream().findAny().orElse(null);
    }

    @Override
    public boolean isExistByEmail(String email) {
        TypedQuery<UserEntity> q = entityManager.createQuery(
                "SELECT e FROM UserEntity e WHERE e.email =: email", UserEntity.class);
        q.setParameter("email", email);
        Optional<UserEntity> user = q.getResultStream().findAny();
        return user.isPresent();
    }
}
