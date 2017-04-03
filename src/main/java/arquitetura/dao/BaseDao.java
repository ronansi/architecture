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
			try {
				T obj = super.persistentClass.newInstance();
				
				for(Entry<String, Object> entry : map.entrySet()){
					Object value = entry.getValue();
					String key = entry.getKey();
					
					if(key.contains(".")){
						String[] keys = key.split("\\.");
						try {
							java.lang.reflect.Field field = super.persistentClass.getDeclaredField(keys[0]);
							field.setAccessible(true);
							
							Object objField = field.get(obj);
							
							if(objField == null){
								objField = field.getType().newInstance();
							}
							
							java.lang.reflect.Field fieldObj = objField.getClass().getDeclaredField(keys[1]);
							fieldObj.setAccessible(true);
							fieldObj.set(objField, value);
							field.set(obj, objField);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							java.lang.reflect.Field field = super.persistentClass.getDeclaredField(key);
							field.setAccessible(true);
							field.set(obj, value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				lista.add(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;
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
