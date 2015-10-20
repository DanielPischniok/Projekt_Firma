package de.jhdp.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.jhdp.model.users.ApplicationUser;
import de.jhdp.model.users.Role;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
public class UserService {

	@PersistenceContext
	private EntityManager em;
	
	public List<ApplicationUser> findAllUsers(){
		return em.createQuery("SELECT au FROM ApplicationUser au left join fetch au.roles",ApplicationUser.class).getResultList();
	}
	
	public List<Role> findAllAvailableRoles(){
		return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
	}
	
	public void saveOrUpdate(ApplicationUser user){
		ApplicationUser mUser = em.merge(user);
		for(Role r: user.getRoles()){
			r.setUser(mUser);
			em.merge(r);
		}
	}

}
