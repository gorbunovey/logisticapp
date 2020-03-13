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
    public void addDriver(DriverEntity driver) {
        entityManager.persist(driver);
    }

    @Override
    public DriverEntity getDriver(int id) {
        // FIXME: use find method instead
        // return entityManager.find(DriverEntity.class, id);
        TypedQuery<DriverEntity> q = entityManager.createQuery("SELECT d FROM DriverEntity d WHERE d.id = :id", DriverEntity.class);
        q.setParameter("id", id);
        return q.getResultStream().findAny().orElse(null);
    }

    @Override
    public void updateDriver(DriverEntity newDriver) {
        //DriverEntity oldDriver = entityManager.find(DriverEntity.class, driver.getId());
        // change oldDriver field by field like newDriver
        //entityManager.remove(driver);
        //entityManager.merge(driver);

    }

    @Override
        public void deleteDriver(int id) {
        // FIXME: нужно уточнить правильную логику работы
        //  пример1: я буду удалять исполняя запрос:
        //  String queryString = "delete from DriverEntity d where d.id = " + id;
        //  Query query = entityManager.createQuery(queryString);
        //  query.executeUpdate();
        //  Будет ли при этом делаться поиск сущности по id в контексте entityManager?
        //  И будет ли она удаляться из контекста?
        //  Насколько ужасен костыль ниже?
        DriverEntity entity = entityManager.find(DriverEntity.class, id);
        if(entity != null) {
            entityManager.merge(entity);
            entityManager.remove(entity);
        }
    }

    @Override
    public List<DriverEntity> getDriverList() {
        return entityManager.createQuery("select d from DriverEntity d").getResultList();
    }
}
