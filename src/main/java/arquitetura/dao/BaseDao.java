package arquitetura.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.dao.jpa.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;

import arquitetura.model.PersistentObject;
import arquitetura.util.UtilReflection;


public class BaseDao<T extends PersistentObject> extends GenericDAOImpl<T, Long> {
	
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
	
	public List<T> list(T entity){
		return list(entity, false, 1);
	}
	
	public List<T> list(T entity, int maxRecursiveLevel){
		return list(entity, false, maxRecursiveLevel);
	}
	
	public List<T> list(T entity, int maxRecursiveLevel, boolean useIlike){
		return list(entity, useIlike, maxRecursiveLevel);
	}
	
	public List<T> list(T entity, boolean useIlike, int maxRecursiveLevel, String... fields){
		
		Search search = new Search(super.persistentClass);
		
		search.setResultMode(Search.RESULT_LIST);
		
		if(fields.length > 0){
			search.setResultMode(Search.RESULT_MAP);
			for(String field : fields){
				search.addField(field, field);
			}
		}
		
		addFiltersForObject(entity, search, useIlike, maxRecursiveLevel, 1, "");
		
		if(fields.length > 0){
			return proccessResult(search(search));			
		}else{
			return search(search);
		}
	}
	
	private List<T> proccessResult(List<Map<String, Object>> result){
		
		List<T> lista = new ArrayList<T>();
		
		for(Map<String, Object> map : result){
			T obj = UtilReflection.getInstance(persistentClass);
			
			for(Entry<String, Object> entry : map.entrySet()){
				buildField(obj, entry.getKey(), entry.getValue());
			}
			
			lista.add(obj);
		}
		return lista;
	}
	
	private void buildField(Object obj, String fieldName, Object value){
		
		if(fieldName.contains(".")){
			String entityName = fieldName.substring(0, fieldName.indexOf("."));
			String nextFieldName = fieldName.substring(fieldName.indexOf(".") + 1, fieldName.length());
			
			Object entity = getEntity(obj, entityName);
			
			buildField(entity, nextFieldName, value);
			
			UtilReflection.setFieldValue(obj, entityName, entity);
			
		}else{
			UtilReflection.setFieldValue(obj, fieldName, value);
		}
	}
	
	private Object getEntity(Object obj, String entityName) {
		
		Object entity = UtilReflection.getFieldValue(obj, entityName);
		
		if(entity == null){
			entity = UtilReflection.getInstance(UtilReflection.getFieldType(obj.getClass(), entityName));
		}
		
		return entity;
	}

	private void addFiltersForObject(Object obj, Search search, boolean useIlike, int maxRecursiveLevel, int recursiveLevel, String superFieldName) {
		
		if(obj != null && recursiveLevel <= maxRecursiveLevel){
			
			java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
			
			for(java.lang.reflect.Field field : fields){
				field.setAccessible(true);
				Object value = null;
				
				try {
					value = field.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				if(value != null){
					
					if(value instanceof PersistentObject){
						
						addFiltersForObject(value, search, useIlike, maxRecursiveLevel, ++recursiveLevel, superFieldName + field.getName() + ".");
						
					} else if(value instanceof String){
						
						if(useIlike){
							search.addFilterILike(superFieldName + field.getName(), "%" + String.valueOf(value) + "%");
						}else{
							search.addFilterEqual(superFieldName + field.getName(), value);
						}
						
					} else if(value instanceof Number){
						search.addFilterEqual(superFieldName + field.getName(), value);
					}
				}
			}
		}
	}
}
