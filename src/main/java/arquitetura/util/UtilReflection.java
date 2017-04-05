package arquitetura.util;

import java.lang.reflect.Field;

public class UtilReflection {

	public static void setFieldValue(Object obj, String fieldName, Object value){
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getFieldValue(Object obj, String fieldName){
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T getInstance(Class<T> clazz){
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Class<?> getFieldType(Class<?> clazz, String fieldName){
		try {
			return clazz.getDeclaredField(fieldName).getType();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Field getField(Class<?> clazz, String fieldName){
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
