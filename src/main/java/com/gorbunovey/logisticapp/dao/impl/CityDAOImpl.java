package com.gorbunovey.logisticapp.dao.impl;

import com.gorbunovey.logisticapp.dao.api.CityDAO;
import com.gorbunovey.logisticapp.entity.CityEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CityDAOImpl implements CityDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CityEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM CityEntity e").getResultList();
    }

    @Override
    public CityEntity getByCode(Long code){
        TypedQuery<CityEntity> q = entityManager.createQuery(
                "SELECT e FROM CityEntity e WHERE e.code =: code", CityEntity.class);
        q.setParameter("code", code);
        return q.getResultStream().findAny().orElse(null);
    }
}
