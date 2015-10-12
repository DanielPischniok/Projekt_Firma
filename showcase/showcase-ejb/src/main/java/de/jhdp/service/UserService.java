package de.jhdp.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.jhdp.model.users.ApplicationUser;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
public class UserService {

	@PersistenceContext
	private EntityManager em;
	
	public List<ApplicationUser> findAllUsers(){
		return em.createQuery("FROM ApplicationUser au",ApplicationUser.class).getResultList();
	}

}
