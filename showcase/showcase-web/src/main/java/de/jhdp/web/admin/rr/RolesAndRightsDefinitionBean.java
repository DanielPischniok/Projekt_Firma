package de.jhdp.web.admin.rr;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.jhdp.model.users.UserRoleDefinition;
import de.jhdp.service.RolesRightsDefinitionService;

@Named("rrBean")
@ViewScoped
public class RolesAndRightsDefinitionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private RolesRightsDefinitionService rrService;
	
	private List<UserRoleDefinition> definedRoles;

	public RolesAndRightsDefinitionBean() {
	}
	
	@PostConstruct
	public void init(){
		definedRoles = rrService.getDefinedRoles();
	}

	public List<UserRoleDefinition> getDefinedRoles() {
		return definedRoles;
	}

	public void setDefinedRoles(List<UserRoleDefinition> definedRoles) {
		this.definedRoles = definedRoles;
	}
	
}
