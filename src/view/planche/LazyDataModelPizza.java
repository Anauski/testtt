package view.planche;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import model.entities.Pizza;
import model.facades.FacadeClientRest;

@SuppressWarnings("serial")
public class LazyDataModelPizza extends LazyDataModel<Pizza> {
	
	@Inject
	private FacadeClientRest facade;

	@Override
	public List<Pizza> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		try {
			List<Pizza>list = facade.listerPizzas(first, pageSize, sortBy, filterBy);
			this.setRowCount((int) this.facade.countPizza(filterBy));
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
