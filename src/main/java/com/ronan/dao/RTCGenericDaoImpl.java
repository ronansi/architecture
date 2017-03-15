package com.ronan.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.dao.jpa.GenericDAOImpl;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;
import com.ronan.model.PersistentObject;


public class RTCGenericDaoImpl<T extends PersistentObject> extends GenericDAOImpl<T, Long> {
	
	@Override
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
	
	@Override
	@Autowired
	public void setSearchProcessor(JPASearchProcessor searchProcessor) {
		super.setSearchProcessor(searchProcessor);
	}
	
}
