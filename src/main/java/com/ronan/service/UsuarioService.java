package com.ronan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronan.dao.UsuarioDao;
import com.ronan.model.Usuario;

@Service
public class UsuarioService extends GenericService<Usuario>{
	
	@Autowired
	public UsuarioService(UsuarioDao usuarioDao) {
		super(usuarioDao);
	}
	
	public List<Usuario> findAll(){
		return dao.list();
	}

	@Override
	protected void beforeSave(Usuario entity) {}

	@Override
	protected void beforeMerge(Usuario entity) {}

}
