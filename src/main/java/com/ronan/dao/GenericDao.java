package com.ronan.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ronan.model.PersistentObject;
import com.ronan.util.Parent;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;


public abstract class GenericDao<T extends PersistentObject> extends Parent {
	
	@PersistenceContext
	protected EntityManager em;
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public List<T> list(){
		return UaiCriteriaFactory.createQueryCriteria(em, clazz).getResultList();
	}
	
	public T find(Long id){
		return em.find(clazz, id);
	}
	
	public T merge(T entity){
		return em.merge(entity);
	}
	
	public void delete(Long id){
		em.remove(find(id));
	}
	
}
