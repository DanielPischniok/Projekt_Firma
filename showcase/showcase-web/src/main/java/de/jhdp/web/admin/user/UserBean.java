package de.jhdp.web.admin.user;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.jhdp.service.UserService;

@Named
@ViewScoped
public class UserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	UserService userService;

	public UserBean() {
	}

}
