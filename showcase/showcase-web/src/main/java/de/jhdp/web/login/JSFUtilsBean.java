package de.jhdp.web.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

@SessionScoped
public class JSFUtilsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public void addGlobalFacesMessage(Severity severity, String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,message,""));
	}


}
