package com.ronan.dao;

import com.googlecode.genericdao.dao.jpa.GenericDAO;
import com.ronan.model.PersistentObject;


public interface RTCGenericDao<T extends PersistentObject> extends GenericDAO<T, Long> {
	
}
