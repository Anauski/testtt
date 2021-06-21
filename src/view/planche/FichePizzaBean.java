package view.planche;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.file.UploadedFile;

import lombok.Getter;
import lombok.Setter;
import model.entities.Pizza;
import model.entities.references.Ingredient;
import model.entities.references.Sauce;
import model.entities.references.Taille;
import model.facades.FacadeClientRest;
import utils.JsfUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FichePizzaBean implements Serializable {

	@Inject
	private FacadeClientRest facadePizza;
	
	@Getter
	private List<Taille> taille = Arrays.asList(Taille.values());
	
	@Getter
	private List<Sauce> base = Arrays.asList(Sauce.values());
	
	@Getter 
	private List<Ingredient> ingredients = Arrays.asList(Ingredient.values());
	

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Getter
	@Setter
	private UploadedFile image;

	@Inject
	@Getter
	@Setter
	private Pizza pizza1;

	@PostConstruct
	public void init() {
		Pizza p = (Pizza) JsfUtils.getFromFlashScope("Pizza");
		if (p != null) {
			this.pizza1 = p;
		}
	}

	public void enregistrer() {
		try {
			if (Objects.isNull(pizza1.getId())) {
				this.facadePizza.sauvegarder(pizza1);
			} else {
				this.facadePizza.modifier(pizza1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}

}
