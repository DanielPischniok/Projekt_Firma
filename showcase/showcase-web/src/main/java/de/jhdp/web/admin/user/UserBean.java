package de.jhdp.web.admin.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import de.jhdp.model.users.ApplicationUser;
import de.jhdp.model.users.Role;
import de.jhdp.model.users.UserRoleDefinition;
import de.jhdp.service.RolesRightsDefinitionService;
import de.jhdp.service.UserService;
import de.jhdp.util.ProjectService;
import de.jhdp.web.login.JSFUtilsBean;

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

	@Inject
	JSFUtilsBean jsfUtils;

	private List<ApplicationUser> allUsers;

	private ApplicationUser currentUser;

	private List<UserRoleDefinition> allRoles;

	private List<String> allRoleNames;

	private List<String> selectedRoleNames;

	private List<UserRoleDefinition> selectedRoles;

	private DualListModel<UserRoleDefinition> dualListRoles;

	private DualListModel<String> dualListRoleNames;

	@PostConstruct
	public void init() {
		allUsers = userService.findAllUsers();
		allRoles = userService.findAllAvailableRoles();
		allRoleNames = userService.findAllAvailableRoleNames();
		selectedRoleNames = new ArrayList<>();
		currentUser = new ApplicationUser();
		selectedRoles = new ArrayList<>();
		dualListRoles = new DualListModel<>(allRoles, selectedRoles);
		dualListRoleNames = new DualListModel<>(allRoleNames, selectedRoleNames);
	}

	public void createNewUser() {
		init();
	}

	public void editCurrentUser(ApplicationUser user) {
		init();
		this.currentUser = user;
		selectedRoleNames = new ArrayList<>();
		for (Role r : user.getRoles()) {
			selectedRoleNames.add(r.getRoleName());
		}
		List<String> names = new ArrayList<>();
		for (Role r : user.getRoles()) {
			names.add(r.getRoleName());
		}
		selectedRoles = userService.findRolesByNames(names);
		for (UserRoleDefinition rd : selectedRoles) {
			Iterator<UserRoleDefinition> iterator = allRoles.iterator();
			while (iterator.hasNext()) {
				UserRoleDefinition rdef = iterator.next();
				if (rdef.getRoleName().equals(rd.getRoleName())) {
					iterator.remove();
				}
			}
		}
		dualListRoles = new DualListModel<>(allRoles, selectedRoles);
		allRoleNames.removeAll(selectedRoleNames);
		dualListRoleNames = new DualListModel<>(allRoleNames, selectedRoleNames);
		dualListRoles = new DualListModel<>(allRoles, selectedRoles);
	}

	public void deleteCurrentUser(ApplicationUser user) {
		userService.deleteUser(user);
		jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_INFO,
				user.getUserIdentifier() + " wurde erfolgreich gelöscht.");
		init();
	}

	public void saveCurrentUser() {
		if (currentUser.getUserId() == null || currentUser.getUserId() == 0) {
			currentUser.setPassword(projectService.generatePassword(currentUser.getPassword()));
		}
		for(String selectedRole: dualListRoleNames.getTarget()){
			if(currentUser.getRoles() == null){
				currentUser.setRoles(new ArrayList<Role>());
			}
			boolean newRole = true;
			for(Role r : currentUser.getRoles()){
				if(r.getRoleName().equals(selectedRole)){
					newRole = false;
				}
			}
			if(newRole){
				Role role = new Role();
				role.setRoleName(selectedRole);
				role.setUser(currentUser);
				currentUser.getRoles().add(role);
			}
		}
		userService.saveOrUpdate(currentUser);
		jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_INFO,
				currentUser.getUserIdentifier() + " wurde erfolgreich gespeichert");
		init();
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(item.getClass().getName());
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<SelectItem> getAllRoleValues() {
		List<SelectItem> result = new ArrayList<>();
		for (UserRoleDefinition role : allRoles) {
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

	public DualListModel<UserRoleDefinition> getDualListRoles() {
		return dualListRoles;
	}

	public void setDualListRoles(DualListModel<UserRoleDefinition> dualListRoles) {
		this.dualListRoles = dualListRoles;
	}

	public DualListModel<String> getDualListRoleNames() {
		return dualListRoleNames;
	}

	public void setDualListRoleNames(DualListModel<String> dualListRoleNames) {
		this.dualListRoleNames = dualListRoleNames;
	}

	public List<UserRoleDefinition> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<UserRoleDefinition> allRoles) {
		this.allRoles = allRoles;
	}

	public List<String> getAllRoleNames() {
		return allRoleNames;
	}

	public void setAllRoleNames(List<String> allRoleNames) {
		this.allRoleNames = allRoleNames;
	}

	public List<String> getSelectedRoleNames() {
		return selectedRoleNames;
	}

	public void setSelectedRoleNames(List<String> selectedRoleNames) {
		this.selectedRoleNames = selectedRoleNames;
	}

}
