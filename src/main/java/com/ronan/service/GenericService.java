package com.ronan.service;

import com.ronan.dao.GenericDao;
import com.ronan.model.PersistentObject;
import com.ronan.util.Parent;

public abstract class GenericService<T extends PersistentObject> extends Parent {
	
	protected GenericDao<T> dao;
	
	public GenericService(GenericDao<T> dao) {
		this.dao = dao;
	}
	
	protected abstract void beforeSave(T entity);
	protected abstract void beforeMerge(T entity);
	
	public T save(T entity){
		
		if(entity.getId() != null){
			beforeMerge(entity);
		}else{
			beforeSave(entity);
		}
		
		return dao.merge(entity);
	}
	
}
