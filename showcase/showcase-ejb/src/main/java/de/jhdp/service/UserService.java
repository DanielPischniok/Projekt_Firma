package de.jhdp.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.jhdp.model.users.ApplicationUser;
import de.jhdp.model.users.Role;
import de.jhdp.model.users.UserRoleDefinition;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
public class UserService {

	@PersistenceContext
	private EntityManager em;
	
	public List<ApplicationUser> findAllUsers(){
		List<ApplicationUser> result =  em.createQuery("SELECT au FROM ApplicationUser au left join fetch au.roles ar",ApplicationUser.class).getResultList();
		for(ApplicationUser u: result){
			u.getRoles().size();
		}
		return result;
	}
	
	public List<UserRoleDefinition> findAllAvailableRoles(){
		return em.createQuery("SELECT r FROM UserRoleDefinition r left join fetch r.attributes", UserRoleDefinition.class).getResultList();
	}
	
	public List<UserRoleDefinition> findRolesByNames(List<String> names){
		return em.createQuery("SELECT DISTINCT(r) FROM UserRoleDefinition r left join fetch r.attributes WHERE r.roleName IN :names", UserRoleDefinition.class)
				.setParameter("names", names).getResultList();
	}
	
	public void deleteUser(ApplicationUser user){
		ApplicationUser mUser = em.merge(user);
		for(Role r: mUser.getRoles()){
			em.remove(r);
		}
		em.remove(user);
	}
	
	public void saveOrUpdate(ApplicationUser user){
		ApplicationUser mUser = em.merge(user);
		for(Role r: user.getRoles()){
			r.setUser(mUser);
			em.merge(r);
		}
	}

}
