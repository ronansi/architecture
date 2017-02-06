package com.ronan.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ronan.model.Usuario;

@Repository
public class UsuarioDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll(){
		return entityManager.createQuery(" select u from Usuario u ").getResultList();
	}
	
}
