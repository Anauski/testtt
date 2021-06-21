package view.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import model.entities.User;
import model.entities.references.Role;
import model.facades.FacadeClientRest;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class UserSessionBean implements Serializable {
	
	@Inject
	private FacadeClientRest facade;
	
	@Getter @Setter
	private User user;
	
	
	public void connecter(String username, String password) {
		this.user = this.facade.username(username, password);
	}
	
	public void disconnect() {
		this.user = null;
	}
	
	public boolean hasRole(String role) {
		if (user == null) return false;
		
		if (user.getRole().equals(Role.valueOf(role)) || user.getRole().getRoles().contains(Role.valueOf(role))) {
			return true;
		}
		
		return false;
	}

}
