package model.entities.dto;

import lombok.Getter;
import lombok.Setter;
import model.entities.references.Role;

public class DtoUser {
	
	@Getter @Setter
	private String username;
	
	@Getter @Setter
	private Role role;

}
