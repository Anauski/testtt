package model.entities;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import model.entities.references.Ingredient;
import model.entities.references.Sauce;
import model.entities.references.Taille;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Pizza implements Serializable {
	
	@Getter @Setter
	private Long id;
	@Getter @Setter
    private String nom;
	@Getter @Setter
    private Taille taille;
	@Getter @Setter
    private Sauce base;
	@Getter @Setter
    private Set<Ingredient> ingredients;
	@Getter @Setter
    private Integer prix;
	@Getter @Setter
    private String image;

}
