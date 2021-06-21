package view.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import model.entities.dto.DtoUser;
import model.facades.FacadeClientRest;
import utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ListeUserBean implements Serializable{

	@Inject
	private FacadeClientRest facade;
	
	@Getter @Setter
	private List<DtoUser> listeUsers = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		try {
			this.listeUsers = facade.listerUsers();
		} catch (Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			JsfUtils.keepMessages();
			FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			JsfUtils.sendRedirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/index.xhtml");
		}
	}
}
