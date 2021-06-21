package model.entities.references;

import lombok.Getter;

public enum Sauce {
	    
	   CREME("Creme"),
	   TOMATE("Tomate"),
	   BARBECUE("Barbecue");
	
	@Getter
	private String libelle;

	
	Sauce (String string) {
		this.libelle=string;
	}
	    
}


