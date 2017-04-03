package arquitetura.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ronan.model.Usuario;

@Repository("usuarioDao")
public class UsuarioDao extends BaseDao<Usuario> {

	public List search(Usuario usuario){
		
		Query query = em().createQuery("select new Map(bean.login as login, bean.senha as senha) from " + Usuario.class.getName() + " bean ");
		
		
		return query.getResultList();
	}
}
