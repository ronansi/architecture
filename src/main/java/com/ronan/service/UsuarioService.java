package com.ronan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronan.dao.UsuarioDao;
import com.ronan.model.Usuario;

@Service
public class UsuarioService extends GenericService<Usuario>{
	
	private UsuarioDao usuariodao;
	
	@Autowired
	public UsuarioService(UsuarioDao usuarioDao) {
		super(usuarioDao);
		
		this.usuariodao = usuarioDao;
	}
	
	public List<Usuario> findAll(){
		return usuariodao.findAll();
	}
	
	@Override
	public Usuario save(Usuario entity) {
		return super.save(entity);
	}

}
