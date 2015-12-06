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
import de.jhdp.model.users.UserRoleDefinition;
import de.jhdp.service.RolesRightsDefinitionService;
import de.jhdp.service.UserService;
import de.jhdp.util.ProjectService;

@Named
@ViewScoped
public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	UserService userService;
	
	@Inject
	RolesRightsDefinitionService rolesAndRightsService;
	
	@Inject
	ProjectService projectService;
	
	private List<ApplicationUser> allUsers;
	
	private ApplicationUser currentUser;
	
	private List<UserRoleDefinition> allRoles;
	
	private List<UserRoleDefinition> selectedRoles;
	
	
	@PostConstruct
	public void init(){
		allUsers = userService.findAllUsers();
		allRoles = userService.findAllAvailableRoles();
	}
	
	public void createNewUser(){
		currentUser = new ApplicationUser();
		selectedRoles = new ArrayList<>();
	}
	
	public void editCurrentUser(ApplicationUser user){
		this.currentUser = user;
		List<String> names = new ArrayList<>();
		for(Role r: user.getRoles()){
			names.add(r.getRoleName());
		}
		selectedRoles = userService.findRolesByNames(names);
	}
	
	public void deleteCurrentUser(ApplicationUser user){
		userService.deleteUser(user);
	}
	
	public void saveCurrentUser(){
		userService.saveOrUpdate(currentUser);
	}
	
	public List<SelectItem> getAllRoleValues(){
		List<SelectItem> result = new ArrayList<>();
		for(UserRoleDefinition role: allRoles){
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

	public List<UserRoleDefinition> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(List<UserRoleDefinition> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}
	
}
