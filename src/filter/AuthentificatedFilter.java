package filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entities.references.Role;
import view.session.UserSessionBean;

@WebFilter("/*")
public class AuthentificatedFilter implements Filter {
	
	@Inject
	private UserSessionBean session;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String url = req.getRequestURI().replace(req.getContextPath(), "");
		
		if(!url.startsWith("/username/username.xhtml") && !url.startsWith("/javax.faces.resource")) {
			if(this.session.getUser() == null) {
				resp.sendRedirect(req.getContextPath()+"/username/username.xhtml");
			}
			
			if(url.startsWith("/pizza/fiche.xhtml") && !session.hasRole(Role.ADMIN.name())) {
				resp.sendRedirect(req.getContextPath()+"/index.xhtml");
			}
			
			if(url.startsWith("/pizza") && !session.hasRole(Role.GESTIONNAIRE.name())) {
				resp.sendRedirect(req.getContextPath()+"/index.xhtml");
			}
			
			if(url.startsWith("/user") && !session.hasRole(Role.ADMIN.name())) {
				resp.sendRedirect(req.getContextPath()+"/index.xhtml");
			}
			
		}
		
		chain.doFilter(request, response);
		
	}

}
