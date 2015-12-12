package de.jhdp.service;

import java.util.ArrayList;
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
		List<ApplicationUser> result =  em.createQuery("SELECT distinct(au) FROM ApplicationUser au left join fetch au.roles ar order by au.userIdentifier",ApplicationUser.class).getResultList();
		for(ApplicationUser u: result){
			u.getRoles().size();
		}
		return result;
	}
	
	public List<UserRoleDefinition> findAllAvailableRoles(){
		return em.createQuery("SELECT r FROM UserRoleDefinition r left join fetch r.attributes", UserRoleDefinition.class).getResultList();
	}
	
	public List<String> findAllAvailableRoleNames(){
		return em.createQuery("SELECT r.roleName FROM UserRoleDefinition r").getResultList();
	}
	
	public List<UserRoleDefinition> findRolesByNames(List<String> names){
		if(names == null || names.isEmpty()){
			return new ArrayList<UserRoleDefinition>();
		}
		return em.createQuery("SELECT DISTINCT(r) FROM UserRoleDefinition r left join fetch r.attributes WHERE r.roleName IN :names", UserRoleDefinition.class)
				.setParameter("names", names).getResultList();
	}
	
	public void deleteUser(ApplicationUser user){
		ApplicationUser mUser = em.merge(user);
		for(Role r: mUser.getRoles()){
			em.remove(r);
		}
		em.remove(mUser);
	}
	
	public void saveOrUpdate(ApplicationUser user){
		ApplicationUser mUser = em.merge(user);
		if(user.getRoles() == null){
			user.setRoles(new ArrayList<Role>());
		}
		for(Role r: user.getRoles()){
			r.setUser(mUser);
			em.merge(r);
		}
	}
	
	public void deleteUserRoles(List<Role> rolesForDelete){
		if(rolesForDelete != null){
			for(Role r: rolesForDelete){
				Role mR = em.merge(r);
				em.remove(mR);
			}
		}
	}

}
