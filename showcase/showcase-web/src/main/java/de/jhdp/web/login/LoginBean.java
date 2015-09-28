package de.jhdp.web.login;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class LoginBean {
	
	private String username, password;
	
	@Inject
	private JSFUtilsBean jsfUtils;

	public LoginBean() {
	}
	
	public String login(){
		HttpServletRequest request = 
				(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		try {
			request.login(username, password);
			jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_INFO, "Login successfull");
		} catch (ServletException e) {
			jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed");
			e.printStackTrace();
		}
		
		return "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
