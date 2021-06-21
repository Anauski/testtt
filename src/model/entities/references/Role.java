package model.entities.references;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public enum Role {
	
	CLIENT,
	GESTIONNAIRE(CLIENT),
	ADMIN(GESTIONNAIRE);
	
	@Getter
	private List<Role> roles = new ArrayList<>();

	private Role(Role... roles) {
		this.roles = Arrays.asList(roles);
	}

}
