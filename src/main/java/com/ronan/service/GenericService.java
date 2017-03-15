package com.ronan.service;

import com.ronan.dao.RTCGenericDao;
import com.ronan.model.PersistentObject;

public abstract class GenericService<T extends PersistentObject> {
	
	protected RTCGenericDao<T> dao;
	
	public GenericService(RTCGenericDao<T> dao) {
		this.dao = dao;
	}
	
	public T save(T entity){
		return dao.save(entity);
	}
	
}
