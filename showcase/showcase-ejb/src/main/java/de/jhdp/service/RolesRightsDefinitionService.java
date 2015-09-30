package de.jhdp.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.jhdp.model.users.UserRoleDefinition;


/**
 * Session Bean implementation class RolesRightsDefinitionService
 */
@Stateless
@LocalBean
public class RolesRightsDefinitionService {
	
	@PersistenceContext
	EntityManager em;

    public RolesRightsDefinitionService() {
    }
    
    public List<UserRoleDefinition> getDefinedRoles(){
    	return em.createQuery("from UserRoleDefinition u", UserRoleDefinition.class).getResultList();
    }

}
