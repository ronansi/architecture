package architecture.service;

import architecture.dao.BaseDao;
import architecture.model.PersistentObject;

public abstract class GenericService<T extends PersistentObject> {
	
	protected BaseDao<T> dao;
	
	public GenericService(BaseDao<T> dao) {
		this.dao = dao;
	}
	
	public T save(T entity){
		return dao.save(entity);
	}
	
}
