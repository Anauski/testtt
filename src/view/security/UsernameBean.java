package view.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AbortProcessingException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import utils.JsfUtils;
import view.session.UserSessionBean;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class UsernameBean implements Serializable {
	
	
	@Inject
	private UserSessionBean session;

	@Getter @Setter
	private String username;
	
	@Getter @Setter
	private String password;
	
	@PostConstruct
	public void init() {
	}
	
	public void connect() {
		this.session.connecter(username, password);
		if (session.getUser() == null) {
			
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_WARN, "username ou password incorrect.");
			throw new AbortProcessingException();
		}
	}
}
