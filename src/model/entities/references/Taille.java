package model.entities.references;

import lombok.Getter;

public enum Taille {
	    
	    SMALL("Small"),
	    LARGE("Large"),
	    EXTRA_LARGE("Extra large");
	    
	
	
	@Getter
	private String libelle;

	
	Taille(String string) {
		this.libelle=string;
	}
	}


