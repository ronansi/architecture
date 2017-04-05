package teste;

public class Teste {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
		
		String key = "pessoa.nome.teste";
		
		System.out.println(key.substring(0, key.indexOf(".")));
		System.out.println(key.substring(key.indexOf(".") + 1, key.length()));
		
//		Usuario usuario = new Usuario();
//		
//		usuario.setId(1L);
//		usuario.setPessoa(new Pessoa("seinao"));
//		
//		
//		
//		Field[] fields = usuario.getClass().getDeclaredFields();
//		
//		for(Field field : fields){
//			field.setAccessible(true);
//			Object value = field.get(usuario);
//
//			if(value != null){
//				if(value instanceof PersistentObject){
//					System.out.println(field.getName());
//					System.out.println("ops");
//				}
//			}
//		}
		
	}

}
