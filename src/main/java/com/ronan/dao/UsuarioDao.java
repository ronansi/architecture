package com.ronan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ronan.model.Usuario;
import com.uaihebert.uaicriteria.UaiCriteria;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;

@Repository
public class UsuarioDao extends GenericDao<Usuario> {

	public List<Usuario> search(Usuario usuario){
		
		UaiCriteria<Usuario> criteria = UaiCriteriaFactory.createQueryCriteria(em, Usuario.class);
		
		criteria.addMultiSelectAttribute("id", "login", "senha");
		
		if(usuario.getLogin() != null){
			criteria.andStringLike(true, "login", "%" + usuario.getLogin() + "%");
		}
		
		return criteria.getResultList();
	}
}
