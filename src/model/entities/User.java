package model.entities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.entities.references.Role;

@ToString
public class User {
	
	@Getter @Setter
	private String username;
	
	@Getter @Setter
	private Role role;
	
	@Getter @Setter
	private String jwtToken;

	@SuppressWarnings("rawtypes")
	public User(String jwtToken) {
		this.jwtToken = jwtToken;
		
		
		String[] splitToken = this.jwtToken.substring("Bearer".length()).trim().split("\\.");
		String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
				
		Jwt<Header,Claims> token = Jwts.parserBuilder().build().parseClaimsJwt(unsignedToken);
		this.username = token.getBody().getSubject();
		this.role = Role.valueOf((String) token.getBody().get("role"));
	}
	
	

}
