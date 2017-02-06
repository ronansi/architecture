package com.ronan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronan.dao.UsuarioDao;
import com.ronan.model.Usuario;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	public List<Usuario> findAll(){
		return usuarioDao.findAll();
	}

}
