package view.planche;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class IndexBean implements Serializable{

	@Getter @Setter
	private String pwd;
	
	@Getter @Setter
	private String msg;
	
	@Getter @Setter
	private String user;
	
	
	
}
