package de.jhdp.web.login;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String username, password;
	
	@Inject
	private JSFUtilsBean jsfUtils;

	public LoginBean() {
	}
	
	public String login(){
		String originalURI = 
				(String) FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get("redirect");
		HttpServletRequest request = 
				(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		try {
			request.login(username, password);
			jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_INFO, "Login successfull");
			if(originalURI != null && originalURI.length() > 0){
				FacesContext.getCurrentInstance().getExternalContext().redirect(originalURI);
			}
		} catch (ServletException | IOException e) {
			jsfUtils.addGlobalFacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed");
			e.printStackTrace();
		}
		
		return "/restricted/start.jsf?faces-redirect=true";
	}
	
	private FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public String getSignedUser(){
		if(getFacesContext().getExternalContext().getUserPrincipal() != null){
			return getFacesContext().getExternalContext().getUserPrincipal().getName();
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
