package de.jhdp.web.admin.rr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.jhdp.model.users.UserRoleAttributeDefinition;
import de.jhdp.model.users.UserRoleDefinition;
import de.jhdp.service.RolesRightsDefinitionService;
import de.jhdp.util.ExistingUsersForRoleException;
import de.jhdp.web.login.JSFUtilsBean;

@Named("rrBean")
@ViewScoped
public class RolesAndRightsDefinitionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private RolesRightsDefinitionService rrService;
	
	@Inject
	private JSFUtilsBean jsfUtils;
	
	private List<UserRoleDefinition> definedRoles;
	
	private UserRoleDefinition currentRole;
	
	private UserRoleAttributeDefinition currentAttribute;
	
	private List<UserRoleAttributeDefinition> deletedAttributes;

	public RolesAndRightsDefinitionBean() {
	}
	
	@PostConstruct
	public void init(){
		definedRoles = rrService.getDefinedRoles();
	}
	
	public String editUserRole(UserRoleDefinition role){
		this.currentRole = role;
		return "";
	}
	
	public String createNewRole(){
		this.currentRole = new UserRoleDefinition();
		return "";
	}
	
	public String saveCurrentRole(){
		try{
			if(rrService.findRoleDefinitionByName(currentRole) != null){
				jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_ERROR, "Der Name der Rolle muss eindeutig sein");
				FacesContext.getCurrentInstance().validationFailed();
				return "";
			}
			
			rrService.saveOrUpdate(currentRole, deletedAttributes);
		}catch(Exception e){
			jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_ERROR, "Der Name der Rolle muss eindeutig sein");
		}
		
		currentRole = new UserRoleDefinition();
		deletedAttributes = new ArrayList<>();
		currentAttribute = new UserRoleAttributeDefinition();
		this.init();
		jsfUtils.addGlobalFacesMessageBundle(FacesMessage.SEVERITY_INFO, "message_save_success");
		return "";
	}
	
	public String deleteCurrentRoleDef(UserRoleDefinition role){
		this.currentRole = new UserRoleDefinition();
		try{
			rrService.deleteRoleDefinition(role);
			jsfUtils.addGlobalFacesMessageBundle(FacesMessage.SEVERITY_INFO, "message_delete_success");
			this.init();
		}catch(Exception e){
			jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_ERROR, "Es gibt User, die der Rolle angehören");
		}
		
		
		return "";
	}
	
	public String createNewAttribute(){
		this.currentAttribute = new UserRoleAttributeDefinition();
		return "";
	}
	
	public String saveCurrentAttribute(){
		boolean unique = true;
		for(UserRoleAttributeDefinition attr: currentRole.getAttributes()){
			if(attr.getAttributeName().equalsIgnoreCase(currentAttribute.getAttributeName())){
				unique = false;
			}
		}
		if(unique){
			currentRole.getAttributes().add(currentAttribute);
			
		}else{
			jsfUtils.addGlobalFacesMessageBundle(FacesMessage.SEVERITY_ERROR, "attr_name_not_unique");
		}
		this.currentAttribute = new UserRoleAttributeDefinition();
		return "";
	}
	
	public void editCurrentAttribute(UserRoleAttributeDefinition attr){
		this.currentAttribute = attr;
	}
	
	public void deleteCurrentAttribute(UserRoleAttributeDefinition attr){
		if(deletedAttributes == null){
			deletedAttributes = new ArrayList<UserRoleAttributeDefinition>();
		}
		deletedAttributes.add(attr);
		currentRole.getAttributes().remove(attr);
		this.currentAttribute = new UserRoleAttributeDefinition();
		init();
	}

	public List<UserRoleDefinition> getDefinedRoles() {
		return definedRoles;
	}

	public void setDefinedRoles(List<UserRoleDefinition> definedRoles) {
		this.definedRoles = definedRoles;
	}

	public UserRoleDefinition getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(UserRoleDefinition currentRole) {
		this.currentRole = currentRole;
	}

	public UserRoleAttributeDefinition getCurrentAttribute() {
		return currentAttribute;
	}

	public void setCurrentAttribute(UserRoleAttributeDefinition currentAttribute) {
		this.currentAttribute = currentAttribute;
	}

	public List<UserRoleAttributeDefinition> getDeletedAttributes() {
		return deletedAttributes;
	}

	public void setDeletedAttributes(List<UserRoleAttributeDefinition> deletedAttributes) {
		this.deletedAttributes = deletedAttributes;
	}
	
}
