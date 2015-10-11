package de.jhdp.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.jhdp.model.users.UserRoleAttributeDefinition;
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
    
    public void deleteRoleDefinition(UserRoleDefinition role){
    	em.remove(role);
    }

}
