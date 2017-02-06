package com.ronan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ronan.model.Usuario;
import com.ronan.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
		
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Usuario>> findAll(){
		
		List<Usuario> usuarios = usuarioService.findAll();
		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
}
