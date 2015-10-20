package de.jhdp.web.admin.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.jhdp.model.users.ApplicationUser;
import de.jhdp.model.users.Role;
import de.jhdp.service.RolesRightsDefinitionService;
import de.jhdp.service.UserService;

@Named
@ViewScoped
public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	UserService userService;
	
	@Inject
	RolesRightsDefinitionService rolesAndRightsService;
	
	private List<ApplicationUser> allUsers;
	
	private ApplicationUser currentUser;
	
	private List<Role> allRoles;
	
	@PostConstruct
	public void init(){
		allUsers = userService.findAllUsers();
		allRoles = userService.findAllAvailableRoles();
	}
	
	public void createNewUser(){
		currentUser = new ApplicationUser();
	}
	
	public void editCurrentUser(ApplicationUser user){
		this.currentUser = user;
	}
	
	public void saveCurrentUser(){
		
	}
	
	public List<SelectItem> getAllRoleValues(){
		List<SelectItem> result = new ArrayList<>();
		for(Role role: allRoles){
			result.add(new SelectItem(role, role.getRoleName()));
		}
		return result;
	}

	public List<ApplicationUser> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<ApplicationUser> allUsers) {
		this.allUsers = allUsers;
	}

	public ApplicationUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ApplicationUser currentUser) {
		this.currentUser = currentUser;
	}
	
	

}
