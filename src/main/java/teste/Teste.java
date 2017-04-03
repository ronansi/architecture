package teste;

import java.lang.reflect.Field;

import arquitetura.model.PersistentObject;
import br.com.ronan.model.Pessoa;
import br.com.ronan.model.Usuario;

public class Teste {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
		
		Usuario usuario = new Usuario();
		
		usuario.setId(1L);
		usuario.setPessoa(new Pessoa("seinao"));
		
		
		
		Field[] fields = usuario.getClass().getDeclaredFields();
		
		for(Field field : fields){
			field.setAccessible(true);
			Object value = field.get(usuario);

			if(value != null){
				if(value instanceof PersistentObject){
					System.out.println(field.getName());
					System.out.println("ops");
				}
			}
		}
		
	}

}
