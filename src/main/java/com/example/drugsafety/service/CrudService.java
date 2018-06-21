package com.example.drugsafety.service;

import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.drugsafety.util.BeanProcessor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;

@Service
public class CrudService {

    @Autowired
    EntityManager em;

    public <T> T findById(Class<T> type, Object id) {
        return this.em.find(type, id);
    }

    public <T> T create(T t) {
        this.em.persist(t);
        //this.em.flush();
        return t;
    }

    public <T> T update(T entity, T data) {
        try {
            HashMap<String, Object> properties = new HashMap<>();
            BeanUtils.populate(data, properties);
            return this.update(entity, properties);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CrudService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CrudService.class.getName()).log(Level.SEVERE, null, ex);
        }
        BeanProcessor.merge(entity, data);
        this.em.persist(entity);
        return entity;
    }

    public <T> T update(T entity, Map<String, Object> data) {
        BeanProcessor.merge(entity, data);
        this.em.persist(entity);
        //this.em.flush();
        return entity;
    }

    public <T> T update(Class<T> type, Object id, Map<String, Object> data) {
        T entity = this.em.find(type, id);
        if (entity == null) {
            return null;
        }
        return this.update(entity, data);
    }

    public <T> T update(Class<T> type, Object id, T data) {
        T entity = this.em.find(type, id);
        if (entity == null) {
            return null;
        }
        return this.update(entity, data);
    }

    public <T> T remove(T entity) {
        this.em.remove(entity);
        //this.em.flush();
        return entity;
    }

    public <T> T remove(Class<T> type, Object id) {
        T entity = this.em.find(type, id);
        if (entity == null) {
            return null;
        }
        return this.remove(entity);
    }

    public HashMap<String, Object[]> diff(Object record, Object id) {
        Object storedRecord = this.em.find(record.getClass(), id);
        return BeanProcessor.diff(storedRecord, record);
    }

}
