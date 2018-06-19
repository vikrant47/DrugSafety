package com.example.drugsafety.service;

import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.drugsafety.util.BeanUtils;

@Service
public class CrudService {
	@Autowired
	EntityManager entityManager;

	public <T> T create(T t) {
		this.entityManager.persist(t);
		//this.entityManager.flush();
		return t;
	}

	public <T> T update(T entity, T data) {
		BeanUtils.merge(entity, data);
		this.entityManager.persist(entity);
		//this.entityManager.flush();
		return entity;
	}

	public <T> T update(T entity, Map<String, Object> data) {
		BeanUtils.merge(entity, data);
		this.entityManager.persist(entity);
		//this.entityManager.flush();
		return entity;
	}

	public <T> T update(Class<T> type, Object id, Map<String, Object> data) {
		T entity = this.entityManager.find(type, id);
		if (entity == null) {
			return null;
		}
		return this.update(entity, data);
	}

	public <T> T update(Class<T> type, Object id, T data) {
		T entity = this.entityManager.find(type, id);
		if (entity == null) {
			return null;
		}
		return this.update(entity, data);
	}

	public <T> T remove(T entity) {
		this.entityManager.remove(entity);
		//this.entityManager.flush();
		return entity;
	}

	public <T> T remove(Class<T> type, Object id) {
		T entity = this.entityManager.find(type, id);
		if (entity == null) {
			return null;
		}
		return this.remove(entity);
	}

}
