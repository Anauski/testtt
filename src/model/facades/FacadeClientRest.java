package model.facades;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import model.entities.Pizza;
import model.entities.User;
import model.entities.dto.DtoUser;
import model.entities.dto.DtoUserUsername;
import view.session.UserSessionBean;

@Stateless
public class FacadeClientRest {

	@Inject
	private UserSessionBean session;

	private static final String REQUEST_URI_USERNAME = "http://localhost:8080/Server_TestJee/api/user/username";

	private static final String REQUEST_URI_PIZZA = "http://localhost:8080/Server_TestJee/api/pizza";

	private static final String REQUEST_URI_USER = "http://localhost:8080/Server_TestJee/api/user";

	private Client client = ClientBuilder.newClient();

	public List<Pizza> listerPizzas(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) throws Exception {
		
		String sortByString = this.sortByMapToString(sortBy);
		String filterByString = this.filterByMapToString(filterBy);

		try {
			List<Pizza> lsPizzas = this.client.target(FacadeClientRest.REQUEST_URI_PIZZA).queryParam("first", first)
					.queryParam("pageSize", pageSize).queryParam("sortBy", sortByString)
					.queryParam("filterBy", filterByString).request(MediaType.APPLICATION_JSON)
					.header("Authorization", this.session.getUser().getJwtToken()).get(new GenericType<List<Pizza>>() {
					});
			return lsPizzas;
		} catch (Exception e) {
			throw new Exception("Erreur lors du chargement de la liste des pizzas");
		}
	}

	public int countPizza(Map<String, FilterMeta> filterBy) throws Exception {
		String filterByString = this.filterByMapToString(filterBy);
		try {
			Integer count = this.client.target(FacadeClientRest.REQUEST_URI_PIZZA).path("count")
					.queryParam("filterBy", filterByString).request(MediaType.APPLICATION_JSON)
					.header("Authorization", this.session.getUser().getJwtToken()).get(Integer.class);
			return count;
		} catch (Exception e) {
			throw new Exception("Erreur lors du chargement de la liste des pizzas");
		}
	}

	public void supprimer(Pizza pizza) throws Exception {
		int status = this.client.target(FacadeClientRest.REQUEST_URI_PIZZA).path(String.valueOf(pizza.getId()))
				.request(MediaType.APPLICATION_JSON).header("Authorization", this.session.getUser().getJwtToken())
				.delete().getStatus();
		if (status != Status.OK.getStatusCode()) {
			throw new Exception("Erreur lors de la suppression de la pizza");
		}
	}

	public Pizza sauvegarder(Pizza pizza) throws Exception {
		Response resp = this.client.target(FacadeClientRest.REQUEST_URI_PIZZA).request(MediaType.APPLICATION_JSON)
				.header("Authorization", this.session.getUser().getJwtToken())
				.post(Entity.entity(pizza, MediaType.APPLICATION_JSON));

		if (resp.getStatus() == Status.NOT_ACCEPTABLE.getStatusCode()) {
			Map<String, List<String>> map = resp.readEntity(new GenericType<Map<String, List<String>>>() {
			});
			StringBuilder sb = new StringBuilder();
			for (String key : map.keySet()) {
				sb.append(key + ": ");
				for (String e : map.get(key)) {
					sb.append(e + ", ");
				}
			}

			throw new Exception(sb.toString());
		}

		if (resp.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new Exception("Erreur lors de l'enregistrement de la pizza");
		}

		if (resp.getStatus() == Status.UNAUTHORIZED.getStatusCode()) {
			throw new Exception("Erreur lors de l'enregistrement de la pizza");
		}

		return resp.readEntity(Pizza.class);
	}

	public Pizza modifier(Pizza pizza) throws Exception {
		Response resp = this.client.target(FacadeClientRest.REQUEST_URI_PIZZA).path(pizza.getId().toString())
				.request(MediaType.APPLICATION_JSON).header("Authorization", this.session.getUser().getJwtToken())
				.put(Entity.entity(pizza, MediaType.APPLICATION_JSON));

		if (resp.getStatus() == Status.NOT_ACCEPTABLE.getStatusCode()) {
			Map<String, List<String>> map = resp.readEntity(new GenericType<Map<String, List<String>>>() {
			});
			StringBuilder sb = new StringBuilder();
			for (String key : map.keySet()) {
				sb.append(key + ": ");
				for (String e : map.get(key)) {
					sb.append(e + ", ");
				}
			}

			throw new Exception(sb.toString());
		}

		if (resp.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
			throw new Exception("Erreur lors de l'enregistrement de la pizza");
		}

		if (resp.getStatus() == Status.UNAUTHORIZED.getStatusCode()) {
			throw new Exception("Erreur lors de l'enregistrement de la pizza");
		}

		return resp.readEntity(Pizza.class);
	}
	

	public User username(String user, String pwd) {
		DtoUserUsername dto = new DtoUserUsername(user, pwd);

		String token = this.client.target(FacadeClientRest.REQUEST_URI_USERNAME).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON)).getHeaderString("Authorization");

		if (token == null)
			return null;

		return new User(token);
	}

	public List<DtoUser> listerUsers() throws Exception {
		try {
			return this.client.target(FacadeClientRest.REQUEST_URI_USER).request(MediaType.APPLICATION_JSON)
					.header("Authorization", this.session.getUser().getJwtToken())
					.get(new GenericType<List<DtoUser>>() {
					});
		} catch (Exception e) {
			throw new Exception("Erreur lors du chargement des users");
		}
	}

	private String sortByMapToString(Map<String, SortMeta> sortBy) {
		String s = "";
		for (Entry<String, SortMeta> e : sortBy.entrySet()) {
			s += e.getValue().getField() + ":" + (e.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC")
					+ "_";
		}
		if (s.contains("_")) {
		s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	private String filterByMapToString(Map<String, FilterMeta> filterBy) {
		String s = "";
		for (Entry<String, FilterMeta> e : filterBy.entrySet()) {
			s += e.getKey() + ":" + e.getValue().getFilterValue() + "_";
		}
		if (s.contains("_")) {
		s = s.substring(0, s.length() - 1);
		}
		return s;
	}

}
