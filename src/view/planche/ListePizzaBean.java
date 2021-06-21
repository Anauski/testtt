package view.planche;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import model.entities.Pizza;
import model.facades.FacadeClientRest;
import utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ListePizzaBean implements Serializable{

	@Inject
	private FacadeClientRest facadePizza;
	
	@Inject
	@Getter @Setter
	private LazyDataModelPizza listePizzas;
	
	@PostConstruct
	public void init() {
	}
	
	public void delete(Pizza pizza) {
		try {
		this.facadePizza.supprimer(pizza);
		this.init();
		}catch(Exception e) {
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, "impossible de supprimer la pizza");
		}
	}
	

	
	public void modifier(Pizza pizza) {
		JsfUtils.putInFlashScope("pizza",  pizza);
	}
}
