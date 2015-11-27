package de.jhdp.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.jhdp.model.users.ApplicationUser;
import de.jhdp.model.users.UserRoleAttributeDefinition;
import de.jhdp.model.users.UserRoleDefinition;
import de.jhdp.util.ExistingUsersForRoleException;


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
    
    public UserRoleDefinition findRoleDefinitionByName(UserRoleDefinition user){
    	List<UserRoleDefinition> result = em.createQuery("SELECT u FROM UserRoleDefinition u WHERE u.roleName = :roleName AND u.id != :id", UserRoleDefinition.class)
    	.setParameter("roleName", user.getRoleName())
    	.setParameter("id", user.getId()==null?0:user.getId())
    	.getResultList();
    	if(result.isEmpty()){
    		return null;
    	}
    	return result.get(0);
    }
    
    public List<UserRoleDefinition> getDefinedRoles(){
    	return em.createQuery("from UserRoleDefinition u", UserRoleDefinition.class).getResultList();
    }
    
    public void saveOrUpdate(UserRoleDefinition definition, List<UserRoleAttributeDefinition> deletedAttrs){
    	UserRoleDefinition mergedDef = em.merge(definition);
    	em.flush();
    	if(definition.getAttributes() != null){
	    	for(UserRoleAttributeDefinition attr : definition.getAttributes()){
	    		mergedDef.setAttributes(new ArrayList<UserRoleAttributeDefinition>());
	    		attr.setRole(mergedDef);
	    		mergedDef.getAttributes().add(attr);
	    		attr = em.merge(attr);
	    	}
		}
    	
    	if(deletedAttrs != null){
	    	for(UserRoleAttributeDefinition deleted: deletedAttrs){
	    		mergedDef.getAttributes().remove(deleted);
	    		deleted.setRole(null);
	    		em.remove(em.getReference(UserRoleAttributeDefinition.class, deleted.getId()));
	    	}
		}
    }
    
    public void deleteRoleDefinition(UserRoleDefinition role) throws ExistingUsersForRoleException{
    	if(!em.createQuery("SELECT u FROM ApplicationUser u join fetch u.roles ur WHERE ur.roleName = :roleName", ApplicationUser.class)
    		.setParameter("roleName", role.getRoleName()).getResultList().isEmpty()){
    			throw new ExistingUsersForRoleException();
    		}
    	UserRoleDefinition entityRole = em.merge(role);
    	for(UserRoleAttributeDefinition att: entityRole.getAttributes()){
    		em.remove(att);
    	}
    	em.remove(entityRole);
    }

}
